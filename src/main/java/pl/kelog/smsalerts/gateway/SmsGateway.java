package pl.kelog.smsalerts.gateway;

import pl.kelog.smsalerts.dto.MessageDeliveryStatus;

import java.math.BigDecimal;

public interface SmsGateway {
    MessageDeliveryStatus send(String recipient, String text);
    
    BigDecimal accountBalance();
}
