package pl.kelog.smsalerts.kspoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KsPollerScheduledTask {
    
    private final long EVERY_15_MINUTES = 15 * 60 * 1000;
    
    private final KsPollerService service;
    
    private final Logger log = LoggerFactory.getLogger(KsPollerScheduledTask.class);
    
    public KsPollerScheduledTask(KsPollerService service) {
        this.service = service;
    }
    
    @Scheduled(fixedRate = EVERY_15_MINUTES, initialDelay = 5000)
    public void reportCurrentTime() {
        log.info("Starting poll task...");
        service.pollAndSend("Chybie");
        log.info("Polling task finished.");
    }
}
