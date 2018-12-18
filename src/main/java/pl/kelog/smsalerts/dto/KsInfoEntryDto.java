package pl.kelog.smsalerts.dto;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@EqualsAndHashCode(of="detailsUrl")
public class KsInfoEntryDto {
    public final String title;
    public final LocalDate publishedDate;
    public final LocalTime scrapeTime;
    public final String detailsUrl;
    
    public KsInfoEntryDto(String title, LocalDate publishedDate, LocalTime scrapeTime) {
        this(title, publishedDate, scrapeTime, "");
    }
    
}
