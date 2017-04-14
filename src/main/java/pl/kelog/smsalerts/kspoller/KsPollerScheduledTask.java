package pl.kelog.smsalerts.kspoller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KsPollerScheduledTask {
    
    private final long EVERY_15_MINUTES = 15 * 60 * 1000;
    
    private final KsPollerService service;
    
    public KsPollerScheduledTask(KsPollerService service) {
        this.service = service;
    }
    
    @Scheduled(fixedRate = EVERY_15_MINUTES)
    public void reportCurrentTime() {
        service.pollAndSend("Chybie");
    }
}
