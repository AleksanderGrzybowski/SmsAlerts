package pl.kelog.smsalerts.dto;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@EqualsAndHashCode(of="detailsUrl")
public class KsInfoEntryDto {
    public final String title;
    public final LocalDate publishedDate;
    public final String detailsUrl;
    
    public KsInfoEntryDto(String title, LocalDate publishedDate) {
        this(title, publishedDate, "");
    }
    
}
