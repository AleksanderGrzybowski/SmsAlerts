package pl.kelog.smsalerts.ksdownloader;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kelog.smsalerts.ksparser.KsInfoEntryDto;
import pl.kelog.smsalerts.ksparser.KsParserService;

import java.util.List;

@Service
class KsDownloaderServiceImpl implements KsDownloaderService {
    
    private final String KS_INFO_SITE_URL = "http://kolejeslaskie.com/category/informacje/";
    private final KsParserService parserService;
    
    public KsDownloaderServiceImpl(KsParserService parserService) {
        this.parserService = parserService;
    }
    
    public List<KsInfoEntryDto> downloadFirstPage() {
        return parserService.parse(
                new RestTemplate().getForEntity(KS_INFO_SITE_URL, String.class).getBody()
        );
    }
}
