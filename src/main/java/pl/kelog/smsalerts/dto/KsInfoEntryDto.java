package pl.kelog.smsalerts.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class KsInfoEntryDto {
    public final String title;
    public final LocalDateTime publishedDate;
    public final String detailsUrl;
    
    public KsInfoEntryDto(String title, LocalDateTime publishedDate) {
        this(title, publishedDate, "");
    }
}
