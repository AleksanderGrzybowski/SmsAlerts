package pl.kelog.smsalerts.sms;

import org.springframework.stereotype.Service;

@Service
public interface GatewayService {
    
    MessageDeliveryStatus send(String recipient, String text);
}
