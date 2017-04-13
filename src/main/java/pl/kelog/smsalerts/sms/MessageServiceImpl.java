package pl.kelog.smsalerts.sms;

import pl.kelog.smsalerts.gateway.GatewayService;
import pl.kelog.smsalerts.validation.ValidationException;

import java.util.List;

class MessageServiceImpl implements MessageService {
    
    private final GatewayService gatewayService;
    private final MessageRepository repository;
    
    public MessageServiceImpl(GatewayService gatewayService, MessageRepository repository) {
        this.gatewayService = gatewayService;
        this.repository = repository;
    }
    
    @Override
    public List<Message> list() {
        return repository.findAll();
    }
    
    @Override
    public MessageDeliveryStatus sendAndStore(String recipient, String text) {
        ValidationException.builder()
                .add(
                        recipient != null && recipient.startsWith("+"),
                        "recipient",
                        "Recipient phone number can't be empty and must start with '+' sign"
                )
                .add(
                        text != null && text.length() > 0 && text.length() <= 160,
                        "text",
                        "Text must be between 1 and 160 characters"
                )
                .throwIfErrors();
        
        MessageDeliveryStatus status = gatewayService.send(recipient, text);
        repository.save(new Message(recipient, text, status));
        return status;
    }
}
