package pl.kelog.smsalerts;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.kelog.smsalerts.gateway.SmsGateway;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Log
class EnsurePositiveAccountBalanceOnStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    
    private final SmsGateway service;
    
    private static final BigDecimal THRESHOLD = new BigDecimal("0.20");
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        BigDecimal balance = service.accountBalance();
        if (balance.compareTo(THRESHOLD) < 0) {
            throw new TooLowAccountBalanceError();
        }
        
        log.info("Sms provider account balance: " + balance);
    }
    
    private static class TooLowAccountBalanceError extends RuntimeException {
        TooLowAccountBalanceError() {
            super("Sms provider account balance is below " + THRESHOLD);
        }
    }
}