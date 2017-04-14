package pl.kelog.smsalerts.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
class GatewayProductionConfiguration {
    
    @Bean
    public GatewayService gatewayServiceImpl() {
        String apiUsername = System.getenv("API_USERNAME");
        String apiPassword = System.getenv("API_PASSWORD");
        
        if (apiUsername == null || apiPassword == null || apiUsername.length() == 0 || apiPassword.length() == 0) {
            throw new RuntimeException("Application running in production, but no credentials provided");
        }
        
        return new GatewayServiceImpl(apiUsername, apiPassword);
    }
}
