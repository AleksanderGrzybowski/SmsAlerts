package pl.kelog.smsalerts.gateway;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.kelog.smsalerts.gateway.bramkasms.BramkasmsConfiguration;

import static java.util.Arrays.asList;
import static pl.kelog.smsalerts.Utils.assertPresent;

/**
 * I've been receiving hundreds of SMS messages and this piece
 * of code has been battle-tested. It's time to rest in peace.
 * 
 * If real SMS sending is to be enabled, please reenable this configuration.
 */
@Configuration
@Profile("this-is-disabled")
@Log
class GatewayProductionConfiguration {
    
    @Bean
    public SmsGateway gatewayService(
            @Value("${smsalerts.apiusername}") String apiUsername,
            @Value("${smsalerts.apipassword}") String apiPassword
    ) {
        assertPresent(asList(apiUsername, apiPassword), NoGatewayCredentialsProvided::new);
        
        log.info("Provided BramkaSms credentials (user:" + apiUsername + ").");
        return new BramkasmsConfiguration().smsGateway(apiUsername, apiPassword);
    }
    
    private static class NoGatewayCredentialsProvided extends RuntimeException {
        NoGatewayCredentialsProvided() {
            super("Application running in production, but no credentials provided");
        }
    }
}
