package pl.kelog.smsalerts.ksdownloader;

import org.springframework.web.client.RestTemplate;
import pl.kelog.smsalerts.ksparser.KsInfoEntryDto;
import pl.kelog.smsalerts.ksparser.KsParserService;

import java.util.List;

class KsDownloaderServiceImpl implements KsDownloaderService {
    private final KsParserService parserService;
    
    public KsDownloaderServiceImpl(KsParserService parserService) {
        this.parserService = parserService;
    }
    
    public List<KsInfoEntryDto> downloadFirstPage() {
        return parserService.parse(
                new RestTemplate().getForEntity(
                        "http://kolejeslaskie.com/category/informacje/",
                        String.class
                ).getBody()
        );
    }
}
