package pl.kelog.smsalerts.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Log
@RequiredArgsConstructor
class BramkasmsGatewayService implements GatewayService {
    
    private final String API_SEND_URL = "https://api.gsmservice.pl/v5/send.php";
    private final String API_BALANCE_URL = "https://api.gsmservice.pl/v5/balance.php";
    private final String SMS_TYPE_ECO = "3";
    
    private final String apiPassword;
    private final String apiUsername;
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        log.info("Preparing to send message '" + text + "' to " + recipient);
        // http://stackoverflow.com/questions/38372422/how-to-post-form-data-with-spring-resttemplate
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("login", apiUsername);
        bodyParams.add("pass", apiPassword);
        bodyParams.add("recipient", recipient);
        bodyParams.add("message", text);
        bodyParams.add("msg_type", SMS_TYPE_ECO);
        
        ResponseEntity<String> response = new RestTemplate().postForEntity(
                API_SEND_URL,
                new HttpEntity<>(bodyParams, headers),
                String.class
        );
        
        if (response.getBody().startsWith("OK")) {
            log.info("Message delivered successully to gateway");
            return MessageDeliveryStatus.OK;
        } else {
            log.severe("Message delivery failed, " + response.getBody());
            return MessageDeliveryStatus.FAILED;
        }
    }
    
    @Override
    public BigDecimal accountBalance() {
        log.info("Checking account balance...");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("login", apiUsername);
        bodyParams.add("pass", apiPassword);
        
        ResponseEntity<String> response = new RestTemplate().postForEntity(
                API_BALANCE_URL,
                new HttpEntity<>(bodyParams, headers),
                String.class
        );
        
        if (response.getBody().startsWith("OK")) {
            BigDecimal balance = new BigDecimal(response.getBody().split("\\|")[2]).setScale(2, RoundingMode.CEILING);
            log.info("Provider account balance: " + balance);
            return balance;
        } else {
            throw new RuntimeException("Error checking account balance.");
        }
    }
}
