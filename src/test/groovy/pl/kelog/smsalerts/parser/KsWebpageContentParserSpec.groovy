package pl.kelog.smsalerts.parser

import spock.lang.Specification

import java.time.LocalDate
import java.time.Month 

class KsWebpageContentParserSpec extends Specification {

    KsWebpageContentParser parser

    void setup() {
        parser = new KsWebpageContentParser()
    }

    void 'should extract titles and dates from sample page'() {
        given:
        List<KsInfoParsedEntryDto> twoElements = [
                new KsInfoParsedEntryDto(
                        'Pociąg 94311 relacji Oświęcim 19:52 – Lubliniec 22:15 – opóźniony na odjeździe (delayed on route) 12 minut.',
                        LocalDate.of(2018, Month.DECEMBER, 14),
                        'https://kolejeslaskie.com/pociag-94311-relacji-oswiecim-1952-lubliniec-2215-opozniony-na-odjezdzie-delayed-on-route-12-minut/'
                ),
                new KsInfoParsedEntryDto(
                        'Pociąg 94660 relacji Wisła Gł. 19:25 – Katowice 21:45 – opóźniony na trasie (delayed on route) 20 minut.',
                        LocalDate.of(2018, Month.DECEMBER, 14),
                        'https://kolejeslaskie.com/pociag-94660-relacji-wisla-gl-1925-katowice-2145-opozniony-na-trasie-delayed-on-route-20-minut/'
                )
        ]
        when:
        List<KsInfoParsedEntryDto> entries = parser.parse(readResource('1.html'))

        then:
        entries.size() == 20
        entries.subList(0, 2) == twoElements
    }

    String readResource(String filename) {
        return new Scanner(this.getClass().getResourceAsStream('/' + filename), 'UTF-8')
                .useDelimiter('\\A').next()
    }

}