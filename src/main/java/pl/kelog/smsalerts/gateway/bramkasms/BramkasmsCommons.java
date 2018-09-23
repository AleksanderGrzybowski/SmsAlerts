package pl.kelog.smsalerts.gateway.bramkasms;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

class BramkasmsCommons {
    
    static HttpHeaders prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
    
    static MultiValueMap<String, String> prepareBodyWithCredentials(BramkasmsCredentials credentials) {
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("login", credentials.apiUsername);
        bodyParams.add("pass", credentials.apiPassword);
        return bodyParams;
    }
    
    static boolean isGatewaySuccess(ResponseEntity<String> response) {
        return response.getBody().startsWith("OK");
    }
}
