package pl.kelog.smsalerts.sms.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
class GatewayProductionConfiguration {
    
    @Bean
    public GatewayService gatewayServiceImpl() {
        return new GatewayServiceImpl(System.getenv("API_USERNAME"), System.getenv("API_PASSWORD"));
    }
}
