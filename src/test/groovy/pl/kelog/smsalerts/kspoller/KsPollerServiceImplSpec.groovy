package pl.kelog.smsalerts.kspoller

import pl.kelog.smsalerts.ksdownloader.KsDownloaderService
import pl.kelog.smsalerts.ksparser.KsInfoEntryDto
import pl.kelog.smsalerts.sms.MessageService
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month

import static pl.kelog.smsalerts.kspoller.KsPollerServiceImpl.FORMATTER

class KsPollerServiceImplSpec extends Specification {

    KsInfoEntryRepository repository
    KsDownloaderService downloaderService
    MessageService messageService
    String recipient = '+123'

    KsPollerServiceImpl service

    void setup() {
        repository = Mock(KsInfoEntryRepository)
        downloaderService = Mock(KsDownloaderService)
        messageService = Mock(MessageService)
        service = new KsPollerServiceImpl(repository, downloaderService, messageService, recipient)
    }

    void 'given empty datastore should fetch and store last page of results'() {
        given:
        KsInfoEntryDto entry = new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDateTime.now())
        KsInfoEntry entity = new KsInfoEntry(null, entry.title, entry.publishedDate.format(FORMATTER))

        repository.count() >> 0L

        when:
        service.prefetchEntriesIfNonePresent()

        then:
        1 * repository.save(entity)
        1 * downloaderService.downloadFirstPage() >> [entry]
    }

    void 'given nonempty datastore, should not fetch and not fill any data'() {
        given:
        repository.count() >> 1

        when:
        service.prefetchEntriesIfNonePresent()

        then:
        0 * repository.save(_)
    }

    void 'given empty datastore and empty entries download should do nothing'() {
        given:
        repository.countByPublishedDate(_) >> 0

        when:
        service.pollAndSend('Katowice')

        then:
        1 * downloaderService.downloadFirstPage() >> []
        0 * repository.save(_)
        0 * messageService.sendAndStore(_, _)
    }

    void 'given empty datastore, should download all entries and send messages on matching patterns'() {
        given:
        List<KsInfoEntryDto> entries = [
                new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDateTime.now()),
                new KsInfoEntryDto('Opóźnienie Katowice', LocalDateTime.now()),
                new KsInfoEntryDto('Roboty torowe na odcinku Pszczyna-Kobiór', LocalDateTime.now())
        ]
        List<KsInfoEntry> entities = entries.collect {
            new KsInfoEntry(null, it.title, it.publishedDate.format(FORMATTER))
        }

        downloaderService.downloadFirstPage() >> entries

        when:
        service.pollAndSend('Katowice')

        then:
        1 * repository.save(entities[0])
        1 * repository.save(entities[1])
        1 * repository.save(entities[2])
        1 * messageService.sendAndStore(recipient, 'Opóźnienie Katowice')
    }

    void 'given nonempty datastore should download all entries and save and send only on new matching entries'() {
        given:
        List<KsInfoEntryDto> entries = [
                new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDateTime.of(2017, Month.APRIL, 13, 21, 0)),
                new KsInfoEntryDto('Opóźnienie Katowice', LocalDateTime.of(2017, Month.APRIL, 13, 23, 0))
        ]

        repository.countByPublishedDate(LocalDateTime.of(2017, Month.APRIL, 13, 21, 0).format(FORMATTER)) >> 1
        repository.countByPublishedDate(LocalDateTime.of(2017, Month.APRIL, 13, 23, 0).format(FORMATTER)) >> 0
        downloaderService.downloadFirstPage() >> entries

        when:
        service.pollAndSend('Katowice')

        then:
        1 * repository.save(new KsInfoEntry(
                null,
                'Opóźnienie Katowice',
                LocalDateTime.of(2017, Month.APRIL, 13, 23, 0).format(FORMATTER)
        ))

        1 * messageService.sendAndStore(recipient, 'Opóźnienie Katowice')
    }

    void 'should properly match - on empty string should always match any input'() {
        when:
        KsInfoEntryDto entry = new KsInfoEntryDto('Wypadek Ustroń Zdrój', LocalDateTime.of(2017, Month.APRIL, 13, 21, 0))

        then:
        service.shouldSendMessage('', entry)
        service.shouldSendMessage('Ustroń', entry)
        !service.shouldSendMessage('Wypadek Katowice', entry)
    }
}