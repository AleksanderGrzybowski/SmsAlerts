package pl.kelog.smsalerts.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.kelog.smsalerts.gateway.fake.FakeGateway;

@Configuration
@Profile({"development", "production"})
class GatewayDevelopmentConfiguration {
    
    @Bean
    public SmsGateway gatewayService() {
        return new FakeGateway();
    }
}
