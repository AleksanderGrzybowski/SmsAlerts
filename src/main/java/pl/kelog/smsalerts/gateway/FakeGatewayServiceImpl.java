package pl.kelog.smsalerts.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

import java.math.BigDecimal;

class FakeGatewayServiceImpl implements GatewayService {
    
    private final Logger log = LoggerFactory.getLogger(FakeGatewayServiceImpl.class);
    
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
