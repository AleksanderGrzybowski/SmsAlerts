package pl.kelog.smsalerts.parser

import pl.kelog.smsalerts.dto.KsInfoEntryDto
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.Month 

class KsWebpageContentParserSpec extends Specification {

    KsWebpageContentParser parser

    void setup() {
        parser = new KsWebpageContentParser()
    }

    void 'should extract titles and dates from sample page'() {
        given:
        List<KsInfoEntryDto> twoElements = [
                new KsInfoEntryDto(
                        'Pociąg nr 44809 relacji  Katowice 12:24 – Bohumin 14:00 – opóźniony na trasie  (delayed on route) 10 minut.',
                        LocalDateTime.of(2019, Month.NOVEMBER, 21, 13, 31),
                        'https://www.kolejeslaskie.com/pociag-nr-44809-relacji-katowice-1224-bohumin-1400-opozniony-na-trasie-delayed-on-route-10-minut-6/'
                ),
                new KsInfoEntryDto(
                        'Pociąg nr 94143 relacji Katowice 10:57 – Cieszyn 12:34 – opóźniony na trasie (delayed on route) 18 minut.',
                        LocalDateTime.of(2019, Month.NOVEMBER, 21, 12, 23),
                        'https://www.kolejeslaskie.com/pociag-nr-94143-relacji-katowice-1057-cieszyn-1234-opozniony-na-trasie-delayed-on-route-18-minut/'
                )
        ]
        when:
        List<KsInfoEntryDto> entries = parser.parse(readResource('1.html'))

        then:
        entries.size() == 20
        entries.subList(0, 2) == twoElements
    }

    String readResource(String filename) {
        return new Scanner(this.getClass().getResourceAsStream('/' + filename), 'UTF-8')
                .useDelimiter('\\A').next()
    }

}
