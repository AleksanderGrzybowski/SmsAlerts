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
        return new KsPollerServiceImpl(
                repository,
                downloaderService,
                messageService,
                System.getenv("RECIPIENT")
        );
    }
    
    @Bean
    public KsPollerScheduledTask ksPollerScheduledTask(KsPollerService ksPollerService) {
        return new KsPollerScheduledTask(ksPollerService, System.getenv("PATTERN"));
    }
}
