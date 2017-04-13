package pl.kelog.smsalerts.sms.gateway;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

@Service
class GatewayServiceImpl implements GatewayService {
    
    private final String API_SEND_URL = "https://api.gsmservice.pl/v5/send.php";
    private final String SMS_TYPE_ECO = "3";
    
    private final String apiPassword;
    private final String apiUsername;
    
    public GatewayServiceImpl(String apiUsername, String apiPassword) {
        this.apiUsername = apiUsername;
        this.apiPassword = apiPassword;
    }
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
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
    
        return response.getBody().startsWith("OK") ? MessageDeliveryStatus.OK : MessageDeliveryStatus.FAILED;
    }
}
