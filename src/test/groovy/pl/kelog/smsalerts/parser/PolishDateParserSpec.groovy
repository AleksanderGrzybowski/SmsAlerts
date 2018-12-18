package pl.kelog.smsalerts.parser

import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class PolishDateParserSpec extends Specification {

    PolishDateParser parser

    void setup() {
        parser = new PolishDateParser()
    }

    void 'should properly parse both single and double digit date'() {
        expect:
        parser.toLocalDate('1 maja 2017') == LocalDate.of(2017, Month.MAY, 1)
        parser.toLocalDate('10 maja 2017') == LocalDate.of(2017, Month.MAY, 10)
    }
}