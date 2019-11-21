package pl.kelog.smsalerts.parser;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@EqualsAndHashCode
public class KsInfoParsedEntryDto {
    public final String title;
    public final LocalDateTime publishedDateTime;
    public final String detailsUrl;
}
