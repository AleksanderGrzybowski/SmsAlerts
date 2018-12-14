package pl.kelog.smsalerts.parser

import pl.kelog.smsalerts.dto.KsInfoEntryDto
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

class KsWebpageContentParserSpec extends Specification {

    KsWebpageContentParser parser

    void setup() {
        parser = new KsWebpageContentParser()
    }

    void 'should extract titles and dates from sample page'() {
        given:
        List<KsInfoEntryDto> twoElements = [
                new KsInfoEntryDto(
                        'Pociąg 94311 relacji Oświęcim 19:52 &#8211; Lubliniec 22:15 &#8211; opóźniony na odjeździe (delayed on route) 12 minut.',
                        LocalDate.of(2018, Month.DECEMBER, 14),
                        'https://kolejeslaskie.com/pociag-94311-relacji-oswiecim-1952-lubliniec-2215-opozniony-na-odjezdzie-delayed-on-route-12-minut/'
                ),
                new KsInfoEntryDto(
                        'Pociąg 94660 relacji Wisła Gł. 19:25 &#8211; Katowice 21:45 &#8211; opóźniony na trasie (delayed on route) 20 minut.',
                        LocalDate.of(2018, Month.DECEMBER, 14),
                        'https://kolejeslaskie.com/pociag-94660-relacji-wisla-gl-1925-katowice-2145-opozniony-na-trasie-delayed-on-route-20-minut/'
                )
        ]
        when:
        List<KsInfoEntryDto> entries = parser.parse(readResource('1.html'))

        then:
        entries.size() == 20
        entries.subList(0, 2) == twoElements
    }

    void 'should properly parse both single and double digit date'() {
        given:
        DateTimeFormatter formatter = KsWebpageContentParser.getFormatter()

        expect:
        LocalDate.parse('1 maja 2017', formatter) == LocalDate.of(2017, Month.MAY, 1,)
        LocalDate.parse('10 maja 2017', formatter) == LocalDate.of(2017, Month.MAY, 10)
    }

    String readResource(String filename) {
        return new Scanner(this.getClass().getResourceAsStream('/' + filename), 'UTF-8')
                .useDelimiter('\\A').next()
    }

}