package pl.kelog.smsalerts.kspoller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kelog.smsalerts.ksdownloader.KsDownloaderService;
import pl.kelog.smsalerts.sms.MessageService;

@Configuration
public class KsPollerConfiguration {
    
    @Bean
    public KsPollerService ksPollerService(
            KsInfoEntryRepository repository,
            KsDownloaderService downloaderService,
            MessageService messageService
    ) {
        String recipient = System.getenv("RECIPIENT");
        if (recipient == null || recipient.length() == 0) {
            throw new RuntimeException("No recipient phone number provided");
        }
        
        return new KsPollerServiceImpl(
                repository,
                downloaderService,
                messageService,
                recipient
        );
    }
    
    @Bean
    public KsPollerScheduledTask ksPollerScheduledTask(KsPollerService ksPollerService) {
        String pattern = System.getenv("PATTERN");
        if (pattern == null || pattern.length() == 0) {
            throw new RuntimeException("No search pattern provided");
        }
        
        return new KsPollerScheduledTask(ksPollerService, pattern);
    }
}
