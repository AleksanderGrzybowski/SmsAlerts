package pl.kelog.smsalerts.gateway.bramkasms;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.kelog.smsalerts.gateway.SmsGateway;
import pl.kelog.smsalerts.dto.MessageDeliveryStatus;

import java.math.BigDecimal;

@Log
@RequiredArgsConstructor
public class BramkasmsGateway implements SmsGateway {
    
    private final BramkasmsBalanceChecker balanceChecker;
    private final BramkasmsSender sender;
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        return sender.send(recipient, text);
    }
    
    @Override
    public BigDecimal accountBalance() {
        return balanceChecker.accountBalance();
    }
}
