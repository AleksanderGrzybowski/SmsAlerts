package pl.kelog.smsalerts.sms;

import java.util.List;

public interface MessageService {
    MessageDeliveryStatus sendAndStore(String recipient, String text);
    
    List<Message> list();
}
