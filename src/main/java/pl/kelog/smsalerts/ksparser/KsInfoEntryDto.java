package pl.kelog.smsalerts.ksparser;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KsInfoEntryDto {
    public final String title;
    public final LocalDateTime publishedDate;
}
