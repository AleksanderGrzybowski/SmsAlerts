package pl.kelog.smsalerts.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

class FakeGatewayServiceImpl implements GatewayService {
    
    private final Logger log = LoggerFactory.getLogger(FakeGatewayServiceImpl.class);
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        log.info("Sending message: " + recipient + " " + text);
        return MessageDeliveryStatus.OK;
    }
}
