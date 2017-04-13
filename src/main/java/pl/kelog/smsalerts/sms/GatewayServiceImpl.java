package pl.kelog.smsalerts.sms;

import org.springframework.stereotype.Service;

@Service
public class GatewayServiceImpl implements GatewayService {
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        return MessageDeliveryStatus.OK;
    }
}
