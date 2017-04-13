package pl.kelog.smsalerts.sms;

public interface MessageService {
    MessageDeliveryStatus sendAndStore(String recipient, String text);
}
