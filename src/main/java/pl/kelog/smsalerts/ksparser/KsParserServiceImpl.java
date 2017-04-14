package pl.kelog.smsalerts.ksparser;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Service
class KsParserServiceImpl implements KsParserService {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy H:mm", new Locale("pl"));
    
    @Override
    public List<KsInfoEntryDto> parse(String content) {
        return Jsoup.parse(content).select("article").stream().map(entry ->
                new KsInfoEntryDto(
                        entry.select("h1").text(),
                        LocalDateTime.parse(entry.select(".entry-date").text(), FORMATTER)
                )
        ).collect(toList());
    }
}
