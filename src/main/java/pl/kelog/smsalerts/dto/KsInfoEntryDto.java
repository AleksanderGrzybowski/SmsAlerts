package pl.kelog.smsalerts.dto;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "detailsUrl")
public class KsInfoEntryDto {
    public final String title;
    public final LocalDateTime publishedDateTime;
    public final String detailsUrl;
    
    public KsInfoEntryDto(String title, LocalDateTime publishedDateTime) {
        this(title, publishedDateTime, "");
    }
}
