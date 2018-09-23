package pl.kelog.smsalerts.gateway;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.kelog.smsalerts.gateway.bramkasms.BramkasmsConfiguration;

import static pl.kelog.smsalerts.Utils.isNullOrEmpty;

@Configuration
@Profile("production")
@Log
class GatewayProductionConfiguration {
    
    @Bean
    public SmsGateway gatewayService(
            @Value("${smsalerts.apiusername}") String apiUsername,
            @Value("${smsalerts.apipassword}") String apiPassword
    ) {
        if (isNullOrEmpty(apiUsername) || isNullOrEmpty(apiPassword)) {
            throw new NoGatewayCredentialsProvided();
        }
        
        log.info("Provided BramkaSms credentials (user:" + apiUsername + ")");
        return new BramkasmsConfiguration().smsGateway(apiUsername, apiPassword);
    }
    
    private static class NoGatewayCredentialsProvided extends RuntimeException {
        NoGatewayCredentialsProvided() {
            super("Application running in production, but no credentials provided");
        }
    }
}
