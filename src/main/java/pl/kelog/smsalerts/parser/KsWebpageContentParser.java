package pl.kelog.smsalerts.parser;

import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import pl.kelog.smsalerts.dto.KsInfoEntryDto;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Log
public class KsWebpageContentParser {
    
    private final PolishDateParser dateParser = new PolishDateParser();
    
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
                dateParser.toLocalDateTime(date),
                entry.select("h2").select("a").attr("href")
        );
    }
}
