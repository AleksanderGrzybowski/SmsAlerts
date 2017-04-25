package pl.kelog.smsalerts.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
class GatewayProductionConfiguration {
    
    private final Logger log = LoggerFactory.getLogger(GatewayProductionConfiguration.class);
    
    @Bean
    public GatewayService gatewayServiceImpl(
            @Value("${smsalerts.apiusername}") String apiUsername,
            @Value("${smsalerts.apipassword}") String apiPassword
    ) {
        if (apiUsername == null || apiPassword == null || apiUsername.length() == 0 || apiPassword.length() == 0) {
            throw new RuntimeException("Application running in production, but no credentials provided");
        } else {
            log.info("Provided api username (length "
                    + apiUsername.length() + ") and password (length " + apiPassword.length() + ")");
        }
        
        return new GatewayServiceImpl(apiUsername, apiPassword);
    }
}
