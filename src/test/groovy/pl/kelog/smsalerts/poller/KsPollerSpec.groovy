package pl.kelog.smsalerts.poller

import pl.kelog.smsalerts.downloader.KsEntryDownloader
import pl.kelog.smsalerts.dto.KsInfoEntryDto
import pl.kelog.smsalerts.message.MessageCreator
import pl.kelog.smsalerts.sms.MessageService
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

import static KsPoller.FORMATTER

class KsPollerSpec extends Specification {

    KsInfoEntryRepository repository
    KsEntryDownloader downloaderService
    MessageService messageService
    MessageCreator messageCreator
    String recipient = '+123'

    KsPoller service

    void setup() {
        repository = Mock(KsInfoEntryRepository)
        downloaderService = Mock(KsEntryDownloader)
        messageService = Mock(MessageService)
        
        messageCreator = Mock(MessageCreator)
        messageCreator.createMessage(_) >> { KsInfoEntry entry -> entry.title }
    }

    def setupService(List<String> patterns = []) {
        new KsPoller(repository, downloaderService, messageService, messageCreator, recipient, patterns)
    }

    void 'given empty datastore should fetch and store last page of results'() {
        given:
        service = setupService()
        KsInfoEntryDto entry = new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDate.now(), 'http://detailsurl')
        KsInfoEntry entity = new KsInfoEntry(null, entry.title, entry.publishedDate.format(FORMATTER), entry.detailsUrl)

        repository.count() >> 0L

        when:
        service.prefetchEntriesIfNonePresent()

        then:
        1 * repository.save(entity)
        1 * downloaderService.downloadFirstPage() >> [entry]
    }

    void 'given nonempty datastore, should not fetch and not fill any data'() {
        given:
        service = setupService()
        repository.count() >> 1

        when:
        service.prefetchEntriesIfNonePresent()

        then:
        0 * repository.save(_)
    }

    void 'given empty datastore and empty entries download should do nothing'() {
        given:
        service = setupService(['Katowice'])
        repository.countByPublishedDate(_) >> 0

        when:
        service.pollAndSend()

        then:
        1 * downloaderService.downloadFirstPage() >> []
        0 * repository.save(_)
        0 * messageService.sendAndStore(_, _)
    }

    void 'given empty datastore, should download all entries and send messages on matching patterns'() {
        given:
        service = setupService(['Katowice', 'Gliwice'])
        List<KsInfoEntryDto> entries = [
                new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDate.now()),
                new KsInfoEntryDto('Utrudnienia na odcinku Gliwice-Ruda Chebzie', LocalDate.now()),
                new KsInfoEntryDto('Opóźnienie Katowice', LocalDate.now()),
                new KsInfoEntryDto('Roboty torowe na odcinku Pszczyna-Kobiór', LocalDate.now())
        ]
        List<KsInfoEntry> entities = entries.collect {
            new KsInfoEntry(null, it.title, it.publishedDate.format(FORMATTER), '')
        }

        downloaderService.downloadFirstPage() >> entries

        when:
        service.pollAndSend()

        then:
        1 * repository.save(entities[0])
        1 * repository.save(entities[1])
        1 * repository.save(entities[2])
        1 * repository.save(entities[3])
        1 * messageService.sendAndStore(recipient, 'Opóźnienie Katowice')
        1 * messageService.sendAndStore(recipient, 'Utrudnienia na odcinku Gliwice-Ruda Chebzie')
    }

    void 'given nonempty datastore should download all entries and save and send only on new matching entries'() {
        given:
        service = setupService(['Katowice'])
        List<KsInfoEntryDto> entries = [
                new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDate.of(2017, Month.APRIL, 13)),
                new KsInfoEntryDto('Opóźnienie Katowice', LocalDate.of(2017, Month.APRIL, 13))
        ]

        repository.countByPublishedDate(LocalDateTime.of(2017, Month.APRIL, 13, 21, 0).format(FORMATTER)) >> 1
        repository.countByPublishedDate(LocalDateTime.of(2017, Month.APRIL, 13, 23, 0).format(FORMATTER)) >> 0
        downloaderService.downloadFirstPage() >> entries

        when:
        service.pollAndSend()

        then:
        1 * repository.save(new KsInfoEntry(
                null,
                'Opóźnienie Katowice',
                LocalDateTime.of(2017, Month.APRIL, 13, 23, 0).format(FORMATTER),
                ''
        ))

        1 * messageService.sendAndStore(recipient, 'Opóźnienie Katowice')
    }

    void 'should properly match - on empty string should always match any input'() {
        when:
        service = setupService(patterns)
        KsInfoEntryDto entry = new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDate.of(2017, Month.APRIL, 13))

        then:
        shouldSend == service.shouldSendMessage(entry)

        where:
        patterns                    | shouldSend
        ['']                        | true
        ['Ustroń']                  | true
        ['USTROŃ']                  | true
        ['Ustroń', 'Wypadek']       | true
        ['Wypadek Katowice']        | false
        ['Katowice', 'Utrudnienia'] | false
    }
}