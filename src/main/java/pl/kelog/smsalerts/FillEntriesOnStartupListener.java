package pl.kelog.smsalerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.kelog.smsalerts.kspoller.KsPollerService;

@Component
public class FillEntriesOnStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    
    private final KsPollerService service;
    
    @Autowired
    public FillEntriesOnStartupListener(KsPollerService service) {
        this.service = service;
    }
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        service.fillIfEmpty();
    }
}