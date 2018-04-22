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
    
    private static final String API_SEND_URL = "https://api.gsmservice.pl/v5/send.php";
    private static final String API_BALANCE_URL = "https://api.gsmservice.pl/v5/balance.php";
    private static final String SMS_TYPE_ECO = "3";
    
    private final String apiUsername;
    private final String apiPassword;
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        log.info("Preparing to send message '" + text + "' to " + recipient);
        ResponseEntity<String> response = deliverMessageToGateway(recipient, text);
        
        if (isGatewaySuccess(response)) {
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
        ResponseEntity<String> response = checkGatewayBalance();
        
        if (isGatewaySuccess(response)) {
            BigDecimal balance = extractBalanceFromResponse(response);
            log.info("Provider account balance: " + balance);
            return balance;
        } else {
            throw new GatewayBalanceCheckError();
        }
    }
    
    private ResponseEntity<String> deliverMessageToGateway(String recipient, String text) {
        return new RestTemplate().postForEntity(
                API_SEND_URL,
                new HttpEntity<>(
                        prepareBodyForSms(recipient, text),
                        prepareHeaders()
                ),
                String.class
        );
    }
    
    private ResponseEntity<String> checkGatewayBalance() {
        return new RestTemplate().postForEntity(
                API_BALANCE_URL,
                new HttpEntity<>(prepareBodyUserPassword(), prepareHeaders()),
                String.class
        );
    }
    
    private static HttpHeaders prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
    
    private MultiValueMap<String, String> prepareBodyUserPassword() {
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("login", apiUsername);
        bodyParams.add("pass", apiPassword);
        return bodyParams;
    }
    
    private static BigDecimal extractBalanceFromResponse(ResponseEntity<String> response) {
        return new BigDecimal(response.getBody().split("\\|")[2]).setScale(2, RoundingMode.CEILING);
    }
    
    private static boolean isGatewaySuccess(ResponseEntity<String> response) {
        return response.getBody().startsWith("OK");
    }
    
    private MultiValueMap<String, String> prepareBodyForSms(String recipient, String text) {
        MultiValueMap<String, String> bodyParams = prepareBodyUserPassword();
        bodyParams.add("recipient", recipient);
        bodyParams.add("message", text);
        bodyParams.add("msg_type", SMS_TYPE_ECO);
        return bodyParams;
    }
    
    private static class GatewayBalanceCheckError extends RuntimeException {
        GatewayBalanceCheckError() {
            super("Error checking account balance");
        }
    }
}
