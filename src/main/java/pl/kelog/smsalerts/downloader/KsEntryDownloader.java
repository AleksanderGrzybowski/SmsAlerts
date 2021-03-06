package pl.kelog.smsalerts.downloader;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kelog.smsalerts.dto.KsInfoEntryDto;
import pl.kelog.smsalerts.parser.KsInfoParsedEntryDto;
import pl.kelog.smsalerts.parser.KsWebpageContentParser;

import java.util.List;

@Service
@Log
@RequiredArgsConstructor
public class KsEntryDownloader {
    
    private static final String KS_INFO_SITE_URL = "https://www.kolejeslaskie.com/category/informacje/";
    
    private final KsWebpageContentParser parser;
    
    public List<KsInfoEntryDto> downloadFirstPage() {
        log.info("Fetching page...");
        String body = new RestTemplate().getForEntity(KS_INFO_SITE_URL, String.class).getBody();
        log.info("Page fetched successfully, size = " + body.length() + " bytes.");
        
        List<KsInfoEntryDto> entries = parser.parse(body);
        log.info("Received " + entries.size() + " entries.");
        
        return entries;
    }
    
    private KsInfoEntryDto enhanceWithParseTime(KsInfoParsedEntryDto parsedEntry) {
        return new KsInfoEntryDto(
                parsedEntry.title,
                parsedEntry.publishedDateTime,
                parsedEntry.detailsUrl
        );
    }
}
