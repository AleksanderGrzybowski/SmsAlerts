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
                        'Pociąg nr 44809 relacji  Katowice 12:24 – Bohumin 14:00 – opóźniony na trasie  (delayed on route) 10 minut.',
                        LocalDate.of(2019, Month.NOVEMBER, 21),
                        'https://www.kolejeslaskie.com/pociag-nr-44809-relacji-katowice-1224-bohumin-1400-opozniony-na-trasie-delayed-on-route-10-minut-6/'
                ),
                new KsInfoParsedEntryDto(
                        'Pociąg nr 94143 relacji Katowice 10:57 – Cieszyn 12:34 – opóźniony na trasie (delayed on route) 18 minut.',
                        LocalDate.of(2019, Month.NOVEMBER, 21),
                        'https://kolejeslaskie.com/pociag-94311-relacji-oswiecim-1952-lubliniec-2215-opozniony-na-odjezdzie-delayed-on-route-12-minut/'
                )
        ]
        when:
        List<KsInfoParsedEntryDto> entries = parser.parse(readResource('2.html'))

        then:
        entries.size() == 20
        entries.subList(0, 2) == twoElements
    }

    String readResource(String filename) {
        return new Scanner(this.getClass().getResourceAsStream('/' + filename), 'UTF-8')
                .useDelimiter('\\A').next()
    }

}
