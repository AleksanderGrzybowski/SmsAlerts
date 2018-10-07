package pl.kelog.smsalerts;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.kelog.smsalerts.sms.MessageService;

@Component
@Log
class SendAppStartedSmsListener implements ApplicationListener<ApplicationReadyEvent> {
    
    private final MessageService messageService;
    
    private final String recipient;
    private final String commitId;
    
    public SendAppStartedSmsListener(
            MessageService messageService,
            @Value("${smsalerts.recipient}") String recipient,
            @Value("${git.commit.id}") String commitId
    ) {
        this.messageService = messageService;
        this.recipient = recipient;
        this.commitId = commitId;
    }
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Sending startup message...");
        messageService.sendAndStore(recipient, formatMessage());
        log.info("Startup message sent.");
    }
    
    private String formatMessage() {
        return "SmsAlerts (revision " + commitId.substring(0, 6) + ") started successfully!";
    }
}