package pl.kelog.smsalerts.gateway.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.kelog.smsalerts.dto.MessageDeliveryStatus;
import pl.kelog.smsalerts.gateway.SmsGateway;

import java.math.BigDecimal;

/**
 * Use this implementation after Bramkasms account becomes empty.
 */
@Log
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AwsSnsGateway implements SmsGateway {
    
    private static final String REGION = "us-east-1";
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        AmazonSNS client = AmazonSNSClientBuilder.standard().withRegion(REGION).build();
    
        PublishRequest request = new PublishRequest()
                .withMessage(text)
                .withPhoneNumber(recipient);
        
        PublishResult result = client.publish(request);
        
        return MessageDeliveryStatus.OK;
    }
    
    @Override
    public BigDecimal accountBalance() {
        return BigDecimal.valueOf(15000); // :)
    }
}
