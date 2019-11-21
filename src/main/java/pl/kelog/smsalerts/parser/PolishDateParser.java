package pl.kelog.smsalerts.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class PolishDateParser {
    
    private static final DateTimeFormatter KS_WEBSITE_DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "d MMMM yyyy HH:mm",
            new Locale("pl")
    );
    
    LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date, KS_WEBSITE_DATE_FORMATTER);
    }
}
