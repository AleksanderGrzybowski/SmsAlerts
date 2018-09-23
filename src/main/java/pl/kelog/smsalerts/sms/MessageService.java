package pl.kelog.smsalerts.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import pl.kelog.smsalerts.dto.MessageDeliveryStatus;
import pl.kelog.smsalerts.gateway.SmsGateway;
import pl.kelog.smsalerts.validation.ValidationException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log
public class MessageService {
    
    private final SmsGateway smsGateway;
    private final MessageRepository repository;
    
    public List<Message> list() {
        return repository.findAll();
    }
    
    public MessageDeliveryStatus sendAndStore(String recipient, String text) {
        validate(recipient, text);
        
        MessageDeliveryStatus status = smsGateway.send(recipient, text);
        repository.save(new Message(recipient, text, status));
        
        log.info("Stored message log: " + recipient + " " + status);
        return status;
    }
    
    private static void validate(String recipient, String text) {
        ValidationException.builder()
                .add(
                        recipient != null && recipient.startsWith("+"),
                        "recipient",
                        "Recipient phone number can't be empty and must start with '+' sign"
                )
                .add(
                        text != null && text.length() > 0 && text.length() <= 160,
                        "text",
                        "Text must be between 1 and 160 characters"
                )
                .throwIfErrors();
    }
}
