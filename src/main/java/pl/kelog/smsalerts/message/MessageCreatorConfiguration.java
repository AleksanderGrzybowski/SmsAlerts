package pl.kelog.smsalerts.message;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log
class MessageCreatorConfiguration {
    
    @Bean
    public MessageCreator messageCreator(
            @Value("${smsalerts.baseUrl:'http://localhost:8080'}") String baseUrl
    ) {
        return new MessageCreator(baseUrl);
    }
}
