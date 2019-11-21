package pl.kelog.smsalerts.parser

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month 

class PolishDateParserSpec extends Specification {

    PolishDateParser parser

    void setup() {
        parser = new PolishDateParser()
    }

    void 'should properly parse both single and double digit date, ignoring time'() {
        expect:
        parser.toLocalDateTime('1 maja 2017 12:45') == LocalDateTime.of(2017, Month.MAY, 1, 12, 45)
        parser.toLocalDateTime('10 maja 2017 09:45') == LocalDateTime.of(2017, Month.MAY, 10, 9, 45)
    }
}
