package pl.kelog.smsalerts.ksparser;

import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Service
@Log
public class KsParserService {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
            "d MMMM yyyy H:mm",
            new Locale("pl")
    );
    
    public List<KsInfoEntryDto> parse(String content) {
        log.info("Parsing " + content.length() + " bytes...");
        List<KsInfoEntryDto> entries = Jsoup.parse(content)
                .select("article").stream()
                .map(entry -> new KsInfoEntryDto(
                                entry.select("h1").text(),
                                LocalDateTime.parse(entry.select(".entry-date").text(), FORMATTER)
                        )
                ).collect(toList());
        log.info("Parsing finished, " + entries.size() + " entries");
        return entries;
    }
    
    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }
}
