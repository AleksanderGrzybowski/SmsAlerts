package pl.kelog.smsalerts.kspoller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kelog.smsalerts.ksdownloader.KsDownloaderService;
import pl.kelog.smsalerts.sms.MessageService;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@Log
public class KsPollerConfiguration {
    
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
            @Value("${smsalerts.patterns}") String patterns
    ) {
        if (patterns == null) {
            throw new RuntimeException("No search patterns provided");
        } else {
            List<String> splitPatterns = asList(patterns.split(","));
            log.info("Search patterns: " + splitPatterns);
            return new KsPollerScheduledTask(ksPollerService, splitPatterns);
        }
    }
}
