package pl.kelog.smsalerts.ksparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

class KsParserServiceImpl implements KsParserService {
    
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy H:mm", new Locale("pl"));
    
    @Override
    public List<KsInfoEntryDto> parse(String content) {
        Document document = Jsoup.parse(content);
        Elements entries = document.select("article");
        
        return entries.stream().map(entry ->
                new KsInfoEntryDto(
                        entry.select("h1").text(),
                        LocalDateTime.parse(entry.select(".entry-date").text(), FORMATTER)
                )
        ).collect(toList());
    }
}
