package pl.kelog.smsalerts.ksdownloader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kelog.smsalerts.ksparser.KsParserService;

@Configuration
public class KsDownloaderConfiguration {
    
    @Bean
    public KsDownloaderService ksDownloaderService(KsParserService ksParserService) {
        return new KsDownloaderServiceImpl(ksParserService);
    }
    
}
