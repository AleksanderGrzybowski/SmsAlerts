package pl.kelog.smsalerts.kspoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class KsPollerScheduledTask {
    
    private final int FIVE_SECONDS = 5000;
    
    private final KsPollerService service;
    
    private final Logger log = LoggerFactory.getLogger(KsPollerScheduledTask.class);
    private final List<String> patterns;
    
    public KsPollerScheduledTask(KsPollerService service, List<String> patterns) {
        this.service = service;
        this.patterns = patterns;
    }
    
    @Scheduled(fixedRateString = "${smsalerts.interval}", initialDelay = FIVE_SECONDS)
    public void pollTask() {
        log.info("Starting poll task...");
        
        service.pollAndSend(patterns);
        
        log.info("Polling task finished.");
    }
}
