package pl.kelog.smsalerts.poller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kelog.smsalerts.downloader.KsEntryDownloader;
import pl.kelog.smsalerts.message.MessageCreator;
import pl.kelog.smsalerts.sms.MessageService;

import java.util.List;

import static java.util.Arrays.asList;
import static pl.kelog.smsalerts.Utils.assertPresent;

@Configuration
@Log
class KsPollerConfiguration {
    
    @Bean
    public KsPoller ksPollerService(
            KsInfoEntryRepository repository,
            KsEntryDownloader downloader,
            MessageService messageService,
            MessageCreator messageCreator,
            @Value("${smsalerts.recipient}") String recipient,
            @Value("${smsalerts.patterns}") String patterns
    ) {
        assertPresent(recipient, NoRecipientPhoneNumberProvidedError::new);
        log.info("Messages will be sent to " + recipient);
        
        assertPresent(patterns, NoSearchPatternsProvided::new);
        List<String> splitPatterns = asList(patterns.split(","));
        log.info("Search patterns: " + splitPatterns);
        
        return new KsPoller(repository, downloader, messageService, messageCreator, recipient, splitPatterns);
    }
    
    @Bean
    public KsPollerScheduledTask ksPollerScheduledTask(
            KsPoller ksPoller
    ) {
        return new KsPollerScheduledTask(ksPoller);
    }
    
    private static class NoRecipientPhoneNumberProvidedError extends RuntimeException {
        NoRecipientPhoneNumberProvidedError() {
            super("No recipient phone number provided");
        }
    }
    
    private static class NoSearchPatternsProvided extends RuntimeException {
        NoSearchPatternsProvided() {
            super("No search patterns provided");
        }
    }
}
