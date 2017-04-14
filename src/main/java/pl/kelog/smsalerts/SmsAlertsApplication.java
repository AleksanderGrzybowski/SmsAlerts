package pl.kelog.smsalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmsAlertsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SmsAlertsApplication.class, args);
    }
}
