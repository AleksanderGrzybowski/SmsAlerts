package pl.kelog.smsalerts.gateway;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
@Log
class GatewayProductionConfiguration {
    
    @Bean
    public GatewayService gatewayServiceImpl(
            @Value("${smsalerts.apiusername}") String apiUsername,
            @Value("${smsalerts.apipassword}") String apiPassword
    ) {
        if (apiUsername == null || apiPassword == null || apiUsername.length() == 0 || apiPassword.length() == 0) {
            throw new RuntimeException("Application running in production, but no credentials provided");
        } else {
            log.info("Provided Bramkasms credentials (user:" + apiUsername + ")");
        }
        
        return new BramkasmsGatewayService(apiUsername, apiPassword);
    }
}
