package pl.kelog.smsalerts;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.kelog.smsalerts.gateway.GatewayService;

@Component
@Log
class SendAppStartedSmsListener implements ApplicationListener<ApplicationReadyEvent> {
    
    private final GatewayService service;
    
    private final String recipient;
    private final String commitId;
    
    @Autowired
    public SendAppStartedSmsListener(
            GatewayService service,
            @Value("${smsalerts.recipient}") String recipient,
            @Value("${git.commit.id}") String commitId
    ) {
        this.service = service;
        this.recipient = recipient;
        this.commitId = commitId;
    }
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Sending app started message");
        
        service.send(recipient, "SmsAlerts (revision " + commitId.substring(0, 6) + ") started successfully!");
    }
}