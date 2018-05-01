package pl.kelog.smsalerts.message

import pl.kelog.smsalerts.kspoller.KsInfoEntry
import spock.lang.Specification

class MessageCreatorSpec extends Specification {

    MessageCreator service

    void setup() {
        service = new MessageCreator('http://baseurl:8080')
    }

    def 'should add redirect link to short-titled message, without shortening it at all'() {
        when:
        KsInfoEntry entry = createEntry('short title')

        then:
        service.createMessage(entry) == 'short title - http://baseurl:8080/r/123'
    }

    def 'should shorten the message if needed to put full URL URL and not exceed message length limit'() {
        given:
        KsInfoEntry entry = createEntry('long title' * 2000)

        when:
        String content = service.createMessage(entry)

        then:
        content.length() == MessageCreator.MESSAGE_LENGTH_LIMIT
        content.endsWith('http://baseurl:8080/r/123')
    }

    private static KsInfoEntry createEntry(String title) {
        new KsInfoEntry(123, title, null, 'http://someurl')
    }
}
