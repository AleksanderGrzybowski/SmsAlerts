package pl.kelog.smsalerts.sms.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
class GatewayDevelopmentConfiguration {
    
    @Bean
    public GatewayService gatewayServiceImpl() {
        return new FakeGatewayServiceImpl();
    }
}
