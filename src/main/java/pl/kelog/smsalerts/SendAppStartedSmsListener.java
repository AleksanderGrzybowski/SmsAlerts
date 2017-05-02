package pl.kelog.smsalerts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import pl.kelog.smsalerts.gateway.GatewayService;

@Component
public class SendAppStartedSmsListener implements ApplicationListener<ApplicationReadyEvent> {
    
    private final GatewayService service;
    
    private final Logger log = LoggerFactory.getLogger(SendAppStartedSmsListener.class);
    private final String recipient;
    private final String commitId;
    
    @Autowired
    public SendAppStartedSmsListener(
            GatewayService service,
            @Value("${smsalerts.recipient}") String recipient,
            @Value("${git.commit.id}") String commitId
    ) {
        this.service = service;
        this.recipient = recipient;
        this.commitId = commitId;
    }
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Sending app started message");
        
        service.send(recipient, "SmsAlerts (revision " + commitId.substring(0, 6) + ") started successfully!");
    }
    
    // http://www.baeldung.com/spring-git-information
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();
        config.setLocation(new ClassPathResource("git.properties"));
        config.setIgnoreResourceNotFound(true);
        config.setIgnoreUnresolvablePlaceholders(true);
        return config;
    }
}