package pl.kelog.smsalerts.kspoller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kelog.smsalerts.ksdownloader.KsDownloaderService;
import pl.kelog.smsalerts.message.MessageCreator;
import pl.kelog.smsalerts.sms.MessageService;

import java.util.List;

import static java.util.Arrays.asList;
import static pl.kelog.smsalerts.Utils.isNullOrEmpty;

@Configuration
@Log
class KsPollerConfiguration {
    
    @Bean
    public KsPollerService ksPollerService(
            KsInfoEntryRepository repository,
            KsDownloaderService downloaderService,
            MessageService messageService,
            MessageCreator messageCreator,
            @Value("${smsalerts.recipient}") String recipient,
            @Value("${smsalerts.patterns}") String patterns
    ) {
        if (isNullOrEmpty(recipient)) {
            throw new NoRecipientPhoneNumberProvidedError();
        }
        log.info("Messages will be sent to " + recipient);
        
        if (isNullOrEmpty(patterns)) {
            throw new NoSearchPatternsProviced();
        }
        List<String> splitPatterns = asList(patterns.split(","));
        log.info("Search patterns: " + splitPatterns);
        
        return new KsPollerService(repository, downloaderService, messageService, messageCreator, recipient, splitPatterns);
    }
    
    @Bean
    public KsPollerScheduledTask ksPollerScheduledTask(
            KsPollerService ksPollerService
    ) {
        return new KsPollerScheduledTask(ksPollerService);
    }
    
    private static class NoRecipientPhoneNumberProvidedError extends RuntimeException {
        NoRecipientPhoneNumberProvidedError() {
            super("No recipient phone number provided");
        }
    }
    
    private static class NoSearchPatternsProviced extends RuntimeException {
        NoSearchPatternsProviced() {
            super("No search patterns provided");
        }
    }
}
