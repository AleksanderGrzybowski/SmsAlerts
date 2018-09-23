package pl.kelog.smsalerts.gateway.bramkasms;

import lombok.extern.java.Log;
import pl.kelog.smsalerts.gateway.SmsGateway;

import static pl.kelog.smsalerts.Utils.isNullOrEmpty;

@Log
public class BramkasmsConfiguration {
    
    public SmsGateway smsGateway(String apiUsername, String apiPassword) {
        if (isNullOrEmpty(apiUsername) || isNullOrEmpty(apiPassword)) {
            throw new NoGatewayCredentialsProvided();
        }
        
        log.info("Provided Bramkasms credentials (user: " + apiUsername + ")");
        BramkasmsCredentials credentials = new BramkasmsCredentials(apiUsername, apiPassword);
        BramkasmsBalanceChecker balanceChecker = new BramkasmsBalanceChecker(credentials);
        BramkasmsSender sender = new BramkasmsSender(credentials);
        return new BramkasmsGateway(balanceChecker, sender);
    }
    
    private static class NoGatewayCredentialsProvided extends RuntimeException {
        NoGatewayCredentialsProvided() {
            super("Application running in production, but no credentials for BramkaSms provided!");
        }
    }
}
