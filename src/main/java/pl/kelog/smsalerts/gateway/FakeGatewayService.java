package pl.kelog.smsalerts.gateway;

import lombok.extern.java.Log;
import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

import java.math.BigDecimal;

@Log
class FakeGatewayService implements GatewayService {
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        log.info("Fake sending message: " + recipient + " " + text + " with OK status");
        return MessageDeliveryStatus.OK;
    }
    
    @Override
    public BigDecimal accountBalance() {
        return new BigDecimal("12.34");
    }
}
