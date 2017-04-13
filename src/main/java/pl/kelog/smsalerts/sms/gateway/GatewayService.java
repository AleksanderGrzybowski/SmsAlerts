package pl.kelog.smsalerts.sms.gateway;

import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

public interface GatewayService {
    
    MessageDeliveryStatus send(String recipient, String text);
}
