package pl.kelog.smsalerts.sms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kelog.smsalerts.gateway.GatewayService;

@Configuration
class MessageConfiguration {
    
    @Bean
    public MessageService messageService(MessageRepository repository, GatewayService service) {
        return new MessageServiceImpl(service, repository);
    }
}
