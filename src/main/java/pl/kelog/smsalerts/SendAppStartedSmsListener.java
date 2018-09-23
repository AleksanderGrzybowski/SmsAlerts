package pl.kelog.smsalerts;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.kelog.smsalerts.gateway.SmsGateway;

@Component
@Log
class SendAppStartedSmsListener implements ApplicationListener<ApplicationReadyEvent> {
    
    private final SmsGateway gateway;
    
    private final String recipient;
    private final String commitId;
    
    public SendAppStartedSmsListener(
            SmsGateway gateway,
            @Value("${smsalerts.recipient}") String recipient,
            @Value("${git.commit.id}") String commitId
    ) {
        this.gateway = gateway;
        this.recipient = recipient;
        this.commitId = commitId;
    }
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Sending startup message");
        gateway.send(recipient, formatMessage());
        log.info("Startup message sent.");
    }
    
    private String formatMessage() {
        return "SmsAlerts (revision " + commitId.substring(0, 6) + ") started successfully!";
    }
}