package pl.kelog.smsalerts.ksparser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KsParserConfiguration {
    @Bean
    public KsParserService ksParserService() {
        return new KsParserServiceImpl();
    }
}
