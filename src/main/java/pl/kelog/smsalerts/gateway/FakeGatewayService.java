package pl.kelog.smsalerts.gateway;

import lombok.extern.java.Log;
import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

import java.math.BigDecimal;

@Log
class FakeGatewayService implements GatewayService {
    
    private static final String MOCK_ACCOUNT_BALANCE_VALUE = "12.34";
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        log.info("Faking sending message: " + recipient + " " + text + " with OK status.");
        return MessageDeliveryStatus.OK;
    }
    
    @Override
    public BigDecimal accountBalance() {
        return new BigDecimal(MOCK_ACCOUNT_BALANCE_VALUE);
    }
}
