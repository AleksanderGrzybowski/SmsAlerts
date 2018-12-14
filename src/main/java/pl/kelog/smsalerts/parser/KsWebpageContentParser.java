package pl.kelog.smsalerts.parser;

import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import pl.kelog.smsalerts.dto.KsInfoEntryDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Service
@Log
public class KsWebpageContentParser {
    
    private static final DateTimeFormatter KS_WEBSITE_DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "d MMMM yyyy",
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
        // https://stackoverflow.com/questions/28295504/how-to-trim-no-break-space-in-java
        String date = entry.select(".post-date-xs")
                .get(0).ownText()
                .replaceAll("(^\\h*)|(\\h*$)", "")
                .trim();
        
        return new KsInfoEntryDto(
                entry.select("h2").select("a").text(),
                LocalDate.parse(date, KS_WEBSITE_DATE_FORMATTER),
                entry.select("h2").select("a").attr("href")
        );
    }
    
    static DateTimeFormatter getFormatter() {
        return KS_WEBSITE_DATE_FORMATTER;
    }
}
