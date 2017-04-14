package pl.kelog.smsalerts.ksdownloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kelog.smsalerts.ksparser.KsInfoEntryDto;
import pl.kelog.smsalerts.ksparser.KsParserService;

import java.util.List;

@Service
class KsDownloaderServiceImpl implements KsDownloaderService {
    
    private final String KS_INFO_SITE_URL = "http://kolejeslaskie.com/category/informacje/";
    private final KsParserService parserService;
    
    private final Logger log = LoggerFactory.getLogger(KsDownloaderServiceImpl.class);
    
    public KsDownloaderServiceImpl(KsParserService parserService) {
        this.parserService = parserService;
    }
    
    public List<KsInfoEntryDto> downloadFirstPage() {
        log.info("Fetching page...");
        String body = new RestTemplate().getForEntity(KS_INFO_SITE_URL, String.class).getBody();
        log.info("Page fetched successfully, size = " + body.length() + " bytes.");
        
        List<KsInfoEntryDto> entries = parserService.parse(body);
        log.info("Received " + entries.size() + " entries.");
        return entries;
    }
}
