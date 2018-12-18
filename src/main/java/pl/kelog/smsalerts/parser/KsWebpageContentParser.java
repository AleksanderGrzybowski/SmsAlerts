package pl.kelog.smsalerts.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import pl.kelog.smsalerts.dto.KsInfoEntryDto;
import pl.kelog.smsalerts.util.NowProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Service
@Log
@RequiredArgsConstructor
public class KsWebpageContentParser {
    
    private final NowProvider nowProvider;
    
    private static final DateTimeFormatter KS_WEBSITE_DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "d MMMM yyyy",
            new Locale("pl")
    );
    
    public List<KsInfoEntryDto> parse(String content) {
        log.info("Parsing " + content.length() + " bytes...");
        
        List<KsInfoEntryDto> entries = Jsoup.parse(content)
                .select("article").stream()
                .map(this::extractEntry)
                .collect(toList());
        
        log.info("Parsing finished, " + entries.size() + " entries");
        return entries;
    }
    
    private KsInfoEntryDto extractEntry(Element entry) {
        // https://stackoverflow.com/questions/28295504/how-to-trim-no-break-space-in-java
        String date = entry.select(".post-date-xs")
                .get(0).ownText()
                .replaceAll("(^\\h*)|(\\h*$)", "")
                .trim();
        
        return new KsInfoEntryDto(
                entry.select("h2").select("a").text(),
                LocalDate.parse(date, KS_WEBSITE_DATE_FORMATTER),
                nowProvider.now(),
                entry.select("h2").select("a").attr("href")
        );
    }
    
    static DateTimeFormatter getFormatter() {
        return KS_WEBSITE_DATE_FORMATTER;
    }
}
