package pl.kelog.smsalerts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.kelog.smsalerts.gateway.GatewayService;

import java.math.BigDecimal;

@Component
public class EnsurePositiveAccountBalanceOnStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    
    private final GatewayService service;
    
    private final Logger log = LoggerFactory.getLogger(EnsurePositiveAccountBalanceOnStartupListener.class);
    
    @Autowired
    public EnsurePositiveAccountBalanceOnStartupListener(GatewayService service) {
        this.service = service;
    }
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        BigDecimal balance = service.accountBalance();
        if (balance.equals(BigDecimal.ZERO)) {
            throw new RuntimeException("Sms provider account balance is 0");
        } else {
            log.info("Sms provider account balance: " + balance);
        }
    }
}