package pl.kelog.smsalerts;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.kelog.smsalerts.kspoller.KsPollerService;

@Component
@RequiredArgsConstructor
class FillEntriesOnStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    
    private final KsPollerService service;
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        service.prefetchEntriesIfNonePresent();
    }
}