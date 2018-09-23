package pl.kelog.smsalerts.gateway.fake;

import lombok.extern.java.Log;
import pl.kelog.smsalerts.gateway.SmsGateway;
import pl.kelog.smsalerts.dto.MessageDeliveryStatus;

import java.math.BigDecimal;

@Log
public class FakeGateway implements SmsGateway {
    
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
