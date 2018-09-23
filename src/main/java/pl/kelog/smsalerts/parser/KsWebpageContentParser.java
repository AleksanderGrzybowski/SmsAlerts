package pl.kelog.smsalerts.parser;

import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import pl.kelog.smsalerts.dto.KsInfoEntryDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Service
@Log
public class KsWebpageContentParser {
    
    private static final DateTimeFormatter KS_WEBSITE_DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "d MMMM yyyy H:mm",
            new Locale("pl")
    );
    
    public List<KsInfoEntryDto> parse(String content) {
        log.info("Parsing " + content.length() + " bytes...");
        
        List<KsInfoEntryDto> entries = Jsoup.parse(content)
                .select("article").stream()
                .map(KsWebpageContentParser::extractEntry)
                .collect(toList());
        
        log.info("Parsing finished, " + entries.size() + " entries");
        return entries;
    }
    
    private static KsInfoEntryDto extractEntry(Element entry) {
        return new KsInfoEntryDto(
                entry.select("h1").text(),
                LocalDateTime.parse(entry.select(".entry-date").text(), KS_WEBSITE_DATE_FORMATTER),
                entry.select("h1").select("a").attr("href")
        );
    }
    
    static DateTimeFormatter getFormatter() {
        return KS_WEBSITE_DATE_FORMATTER;
    }
}