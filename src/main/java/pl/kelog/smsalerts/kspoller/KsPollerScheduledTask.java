package pl.kelog.smsalerts.kspoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;

@Log
@RequiredArgsConstructor
class KsPollerScheduledTask {
    
    private static final int INTERVAL_MS = 5000;
    
    private final KsPollerService service;
    
    @Scheduled(fixedRateString = "${smsalerts.interval}", initialDelay = INTERVAL_MS)
    public void pollTask() {
        log.info("Starting poll task...");
        
        service.pollAndSend();
        
        log.info("Polling task finished");
    }
}
