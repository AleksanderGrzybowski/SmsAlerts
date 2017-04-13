package pl.kelog.smsalerts.sms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Message {
    @Id
    @GeneratedValue
    private Long id;
    
    private String recipient;
    
    private String text;
    
    private MessageDeliveryStatus status;
    
    public Message() {}
    
    public Message(String recipient, String text, MessageDeliveryStatus status) {
        this.recipient = recipient;
        this.text = text;
        this.status = status;
    }
    
    public Message(Long id, String recipient, String text, MessageDeliveryStatus status) {
        this(recipient, text, status);
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public MessageDeliveryStatus getStatus() {
        return status;
    }
    
    public void setStatus(MessageDeliveryStatus status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Message message = (Message) o;
        
        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        if (recipient != null ? !recipient.equals(message.recipient) : message.recipient != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return status == message.status;
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
