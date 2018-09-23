package pl.kelog.smsalerts.gateway.bramkasms;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static pl.kelog.smsalerts.gateway.bramkasms.BramkasmsCommons.isGatewaySuccess;
import static pl.kelog.smsalerts.gateway.bramkasms.BramkasmsCommons.prepareBodyWithCredentials;
import static pl.kelog.smsalerts.gateway.bramkasms.BramkasmsCommons.prepareHeaders;

@Log
@RequiredArgsConstructor
class BramkasmsBalanceChecker {
    
    private static final String API_BALANCE_URL = "https://api.gsmservice.pl/v5/balance.php";
    
    private final BramkasmsCredentials credentials;
    
    BigDecimal accountBalance() {
        log.info("Checking account balance...");
        ResponseEntity<String> response = retrieveBalance();
        
        if (isGatewaySuccess(response)) {
            BigDecimal balance = extractBalanceFromResponse(response);
            log.info("Provider account balance: " + balance);
            return balance;
        } else {
            throw new GatewayBalanceCheckError();
        }
    }
    
    private ResponseEntity<String> retrieveBalance() {
        return new RestTemplate().postForEntity(
                API_BALANCE_URL,
                new HttpEntity<>(prepareBodyWithCredentials(credentials), prepareHeaders()),
                String.class
        );
    }
    
    private static BigDecimal extractBalanceFromResponse(ResponseEntity<String> response) {
        return new BigDecimal(response.getBody().split("\\|")[2]).setScale(2, RoundingMode.CEILING);
    }
    
    private static class GatewayBalanceCheckError extends RuntimeException {
        GatewayBalanceCheckError() {
            super("Error checking account balance");
        }
    }
}
