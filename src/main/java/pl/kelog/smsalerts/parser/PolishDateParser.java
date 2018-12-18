package pl.kelog.smsalerts.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class PolishDateParser {
    
    private static final DateTimeFormatter KS_WEBSITE_DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "d MMMM yyyy",
            new Locale("pl")
    );
    
    LocalDate toLocalDate(String date) {
        return LocalDate.parse(date, KS_WEBSITE_DATE_FORMATTER);
    }
}
