package pl.kelog.smsalerts.ksparser;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class KsParserServiceImplTest {
    
    KsParserService service;
    
    @Before
    public void setup() {
        service = new KsParserServiceImpl();
    }
    
    @Test
    public void should_extract_titles_and_dates_from_sample_page() {
        List<KsInfoEntryDto> twoElements = asList(
                new KsInfoEntryDto(
                        "Katowice 15:34 – Tychy Lodowisko 16:01 – opóźniony na odjeździe (delayed on departure) 10 minut",
                        LocalDateTime.of(2017, Month.APRIL, 13, 15, 55, 0)
                ),
                new KsInfoEntryDto(
                        "Gliwice 13:45 – Częstochowa 15:52 – opóźniony na odjeździe (delayed on departure) 30 minut",
                        LocalDateTime.of(2017, Month.APRIL, 13, 13, 51, 0)
                )
        );
        
        List<KsInfoEntryDto> entries = service.parse(readResource("1.html"));
        
        assertThat(entries).hasSize(20);
        assertThat(entries.subList(0, 2)).isEqualTo(twoElements);
    }
    
    @Test
    public void should_properly_parse_both_single_and_double_digit_date() {
        DateTimeFormatter formatter = KsParserServiceImpl.getFormatter();
        
        assertThat(
                LocalDateTime.parse("1 maja 2017 8:11", formatter)
        ).isEqualTo(LocalDateTime.of(2017, Month.MAY, 1, 8, 11));
        
        assertThat(
                LocalDateTime.parse("10 maja 2017 8:11", formatter)
        ).isEqualTo(LocalDateTime.of(2017, Month.MAY, 10, 8, 11));
    }
    
    private String readResource(String filename) {
        return new Scanner(this.getClass().getResourceAsStream("/" + filename), "UTF-8")
                .useDelimiter("\\A").next();
    }
    
}