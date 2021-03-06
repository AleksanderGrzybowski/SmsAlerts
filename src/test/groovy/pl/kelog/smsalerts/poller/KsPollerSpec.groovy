package pl.kelog.smsalerts.poller

import pl.kelog.smsalerts.downloader.KsEntryDownloader
import pl.kelog.smsalerts.dto.KsInfoEntryDto
import pl.kelog.smsalerts.message.MessageCreator
import pl.kelog.smsalerts.sms.MessageService
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month

import static KsPoller.DATE_FORMATTER 

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
        KsInfoEntryDto entry = new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDateTime.now(), 'http://detailsurl')
        KsInfoEntry entity = new KsInfoEntry(
                null,
                entry.title,
                entry.publishedDateTime.format(DATE_FORMATTER),
                entry.detailsUrl
        )

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
            new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDateTime.now()),
            new KsInfoEntryDto('Utrudnienia na odcinku Gliwice-Ruda Chebzie', LocalDateTime.now()),
            new KsInfoEntryDto('Opóźnienie Katowice', LocalDateTime.now()),
            new KsInfoEntryDto('Roboty torowe na odcinku Pszczyna-Kobiór', LocalDateTime.now())
        ]
        List<KsInfoEntry> entities = entries.collect {
            new KsInfoEntry(null, it.title, it.publishedDateTime.format(DATE_FORMATTER), '')
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
                new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDateTime.of(2017, Month.APRIL, 13, 0, 0)),
                new KsInfoEntryDto('Opóźnienie Katowice', LocalDateTime.of(2017, Month.APRIL, 13, 0, 0))
        ]

        repository.countByDetailsUrl(LocalDateTime.of(2017, Month.APRIL, 13, 21, 0).format(DATE_FORMATTER)) >> 1
        repository.countByDetailsUrl(LocalDateTime.of(2017, Month.APRIL, 13, 23, 0).format(DATE_FORMATTER)) >> 0
        downloaderService.downloadFirstPage() >> entries

        when:
        service.pollAndSend()

        then:
        1 * repository.save(new KsInfoEntry(
                null,
                'Opóźnienie Katowice',
                LocalDateTime.of(2017, Month.APRIL, 13, 0, 0).format(DATE_FORMATTER),
                ''
        ))

        1 * messageService.sendAndStore(recipient, 'Opóźnienie Katowice')
    }

    void 'should properly match - on empty string should always match any input'() {
        when:
        service = setupService(patterns)
        KsInfoEntryDto entry = new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDateTime.of(2017, Month.APRIL, 13, 0, 0))

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