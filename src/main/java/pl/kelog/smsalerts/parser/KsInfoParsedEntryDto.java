package pl.kelog.smsalerts.parser;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@EqualsAndHashCode
public class KsInfoParsedEntryDto {
    public final String title;
    public final LocalDate publishedDate;
    public final String detailsUrl;
}
