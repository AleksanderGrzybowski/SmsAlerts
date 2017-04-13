package pl.kelog.smsalerts.sms.gateway;

import org.springframework.stereotype.Service;
import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

@Service
class GatewayServiceImpl implements GatewayService {
    
    private final String apiPassword;
    private final String apiUsername;
    
    public GatewayServiceImpl(String apiUsername, String apiPassword ) {
        this.apiUsername = apiUsername;
        this.apiPassword = apiPassword;
    }
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        System.out.println("SENDING REAL");
        return MessageDeliveryStatus.OK;
    }
}
