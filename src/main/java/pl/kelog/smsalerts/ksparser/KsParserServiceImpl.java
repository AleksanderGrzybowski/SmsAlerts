package pl.kelog.smsalerts.ksparser;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Service
class KsParserServiceImpl implements KsParserService {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy H:mm", new Locale("pl"));
    
    private final Logger log = LoggerFactory.getLogger(KsParserServiceImpl.class);
    
    @Override
    public List<KsInfoEntryDto> parse(String content) {
        log.info("Parsing " + content.length() + " bytes...");
        List<KsInfoEntryDto> entries = Jsoup.parse(content).select("article").stream().map(entry ->
                new KsInfoEntryDto(
                        entry.select("h1").text(),
                        LocalDateTime.parse(entry.select(".entry-date").text(), FORMATTER)
                )
        ).collect(toList());
        log.info("Parsing finished, " + entries.size() + " entries");
        return entries;
    }
}
