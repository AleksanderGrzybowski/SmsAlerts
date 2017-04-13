package pl.kelog.smsalerts.sms.gateway;

import pl.kelog.smsalerts.sms.MessageDeliveryStatus;

class FakeGatewayServiceImpl implements GatewayService {
    
    @Override
    public MessageDeliveryStatus send(String recipient, String text) {
        System.out.println("Sending message: " + recipient + " " + text);
        return MessageDeliveryStatus.OK;
    }
}
