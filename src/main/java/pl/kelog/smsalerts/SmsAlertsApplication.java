package pl.kelog.smsalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmsAlertsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SmsAlertsApplication.class, args);
    }
    
    // http://www.baeldung.com/spring-git-information
    @Bean
    public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();
        config.setLocation(new ClassPathResource("git.properties"));
        config.setIgnoreResourceNotFound(true);
        config.setIgnoreUnresolvablePlaceholders(true);
        return config;
    }
}
