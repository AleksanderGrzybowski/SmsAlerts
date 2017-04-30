package pl.kelog.smsalerts.kspoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KsPollerScheduledTask {
    
    private final long EVERY_15_MINUTES = 15 * 60 * 1000;
    private final int FIVE_SECONDS = 5000;
    
    private final KsPollerService service;
    
    private final Logger log = LoggerFactory.getLogger(KsPollerScheduledTask.class);
    private final String pattern;
    
    public KsPollerScheduledTask(KsPollerService service, String pattern) {
        this.service = service;
        this.pattern = pattern;
    }
    
    @Scheduled(fixedRate = EVERY_15_MINUTES, initialDelay = FIVE_SECONDS)
    public void pollTask() {
        log.info("Starting poll task...");
        
        service.pollAndSend(pattern);
        
        log.info("Polling task finished.");
    }
}
