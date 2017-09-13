package pl.kelog.smsalerts.ksparser

import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter

class KsParserServiceSpec extends Specification {

    KsParserService service

    void setup() {
        service = new KsParserService()
    }

    void 'should extract titles and dates from sample page'() {
        given:
        List<KsInfoEntryDto> twoElements = [
                new KsInfoEntryDto(
                        'Katowice 15:34 – Tychy Lodowisko 16:01 – opóźniony na odjeździe (delayed on departure) 10 minut',
                        LocalDateTime.of(2017, Month.APRIL, 13, 15, 55, 0)
                ),
                new KsInfoEntryDto(
                        'Gliwice 13:45 – Częstochowa 15:52 – opóźniony na odjeździe (delayed on departure) 30 minut',
                        LocalDateTime.of(2017, Month.APRIL, 13, 13, 51, 0)
                )
        ]
        when:
        List<KsInfoEntryDto> entries = service.parse(readResource('1.html'))

        then:
        entries.size() == 20
        entries.subList(0, 2) == twoElements
    }

    void 'should properly parse both single and double digit date'() {
        given:
        DateTimeFormatter formatter = KsParserService.getFormatter()

        expect:
        LocalDateTime.parse('1 maja 2017 8:11', formatter) == LocalDateTime.of(2017, Month.MAY, 1, 8, 11)
        LocalDateTime.parse('10 maja 2017 8:11', formatter) == LocalDateTime.of(2017, Month.MAY, 10, 8, 11 )
    }

    String readResource(String filename) {
        return new Scanner(this.getClass().getResourceAsStream('/' + filename), 'UTF-8')
                .useDelimiter('\\A').next()
    }

}