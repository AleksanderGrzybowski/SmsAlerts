package pl.kelog.smsalerts.poller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;

@Log
@RequiredArgsConstructor
class KsPollerScheduledTask {
    
    /**
     * There seems to be some race between running this task for the first time
     * and event to trigger first download from FillEntriesOnStartupListener.
     * So we put some delay here as a workaround.
     */
    private static final int INITIAL_DELAY = 10_000;
    
    private final KsPoller poller;
    
    @Scheduled(fixedRateString = "${smsalerts.interval}", initialDelay = INITIAL_DELAY)
    public void pollTask() {
        log.info("Starting poll task...");
        
        poller.pollAndSend();
        
        log.info("Polling task finished");
    }
}
