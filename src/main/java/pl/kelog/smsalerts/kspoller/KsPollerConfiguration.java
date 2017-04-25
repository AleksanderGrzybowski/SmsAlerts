package pl.kelog.smsalerts.kspoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kelog.smsalerts.ksdownloader.KsDownloaderService;
import pl.kelog.smsalerts.sms.MessageService;

@Configuration
public class KsPollerConfiguration {
    
    private final Logger log = LoggerFactory.getLogger(KsPollerConfiguration.class);
    
    @Bean
    public KsPollerService ksPollerService(
            KsInfoEntryRepository repository,
            KsDownloaderService downloaderService,
            MessageService messageService,
            @Value("${smsalerts.recipient}") String recipient
    ) {
        if (recipient == null || recipient.length() == 0) {
            throw new RuntimeException("No recipient phone number provided");
        } else {
            log.info("Messages will be sent to " + recipient);
        }
        
        return new KsPollerServiceImpl(repository, downloaderService, messageService, recipient);
    }
    
    @Bean
    public KsPollerScheduledTask ksPollerScheduledTask(
            KsPollerService ksPollerService,
            @Value("${smsalerts.pattern}") String pattern
    ) {
        if (pattern == null) {
            throw new RuntimeException("No search pattern provided");
        } else {
            log.info("Search pattern: " + pattern);
        }
        
        return new KsPollerScheduledTask(ksPollerService, pattern);
    }
}
