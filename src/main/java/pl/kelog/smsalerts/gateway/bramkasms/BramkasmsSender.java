package pl.kelog.smsalerts.gateway.bramkasms;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

import static pl.kelog.smsalerts.gateway.bramkasms.BramkasmsCommons.isGatewaySuccess;
import static pl.kelog.smsalerts.gateway.bramkasms.BramkasmsCommons.prepareBodyWithCredentials;
import static pl.kelog.smsalerts.gateway.bramkasms.BramkasmsCommons.prepareHeaders;

@RequiredArgsConstructor
@Log
class BramkasmsSender {
    private final BramkasmsCredentials credentials;
    
    private static final String API_SEND_URL = "https://api.gsmservice.pl/v5/send.php";
    private static final String SMS_TYPE_ECO = "3";
    
    MessageDeliveryStatus send(String recipient, String text) {
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
    
    private MultiValueMap<String, String> prepareBodyForSms(String recipient, String text) {
        MultiValueMap<String, String> bodyParams = prepareBodyWithCredentials(credentials);
        bodyParams.add("recipient", recipient);
        bodyParams.add("message", text);
        bodyParams.add("msg_type", SMS_TYPE_ECO);
        return bodyParams;
    }
}
