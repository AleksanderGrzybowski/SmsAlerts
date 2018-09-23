package pl.kelog.smsalerts.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KsInfoEntryDto {
    public final String title;
    public final LocalDateTime publishedDate;
    public final String detailsUrl;
    
    public KsInfoEntryDto(String title, LocalDateTime publishedDate, String detailsUrl) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.detailsUrl = detailsUrl;
    }
    
    public KsInfoEntryDto(String title, LocalDateTime publishedDate) {
        this(title, publishedDate, "");
    }
}
