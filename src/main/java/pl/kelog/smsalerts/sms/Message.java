package pl.kelog.smsalerts.sms;

import lombok.Data;
import pl.kelog.smsalerts.dto.MessageDeliveryStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    
    private String recipient;
    
    private String text;
    
    private MessageDeliveryStatus status;
    
    @SuppressWarnings("unused")
    public Message() {
    }
    
    public Message(String recipient, String text, MessageDeliveryStatus status) {
        this.recipient = recipient;
        this.text = text;
        this.status = status;
    }
    
    public Message(Long id, String recipient, String text, MessageDeliveryStatus status) {
        this(recipient, text, status);
        this.id = id;
    }
}
