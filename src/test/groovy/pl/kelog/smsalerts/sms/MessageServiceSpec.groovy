package pl.kelog.smsalerts.sms

import pl.kelog.smsalerts.gateway.SmsGateway
import pl.kelog.smsalerts.validation.ValidationException
import spock.lang.Specification

class MessageServiceSpec extends Specification {

    MessageService service

    SmsGateway gatewayService
    MessageRepository repository

    void setup() {
        gatewayService = Mock(SmsGateway)
        repository = Mock(MessageRepository)
        service = new MessageService(gatewayService, repository)
    }


    void 'should send message and store it and return status'() {
        given:
        Message sent = new Message(null, '+123', 'text', MessageDeliveryStatus.OK)
        repository.save(sent) >> sent

        when:
        MessageDeliveryStatus status = service.sendAndStore('+123', 'text')

        then:
        status == MessageDeliveryStatus.OK
        1 * gatewayService.send('+123', 'text') >> MessageDeliveryStatus.OK
        1 * repository.save(_)
    }


    void 'should list all messages'() {
        given:
        List<Message> expected = [new Message(1L, '+123', 'text', MessageDeliveryStatus.OK)]

        when:
        List<Message> result = service.list()

        then:
        result == expected
        (1.._) * repository.findAll() >> expected
    }

    void 'should reject empty recipient'() {
        when:
        service.sendAndStore('', 'text')
        
        then:
        thrown ValidationException
    }

    void 'should reject empty text'() {
        when:
        service.sendAndStore('+123', '')
        
        then:
        thrown ValidationException
    }

    void 'should reject text longer than 160 chars'() {
        when:
        service.sendAndStore('+123', 'X' * 161)
        
        then:
        thrown ValidationException
    }
}