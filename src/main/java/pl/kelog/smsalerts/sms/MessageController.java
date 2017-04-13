package pl.kelog.smsalerts.sms;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway")
class MessageController {
    
    private final MessageService service;
    
    public MessageController(MessageService service) {
        this.service = service;
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MessageDeliveryStatusDto> create(@RequestBody SendMessageDto dto) {
        MessageDeliveryStatus status = service.sendAndStore(dto.recipient, dto.text);
        return new ResponseEntity<>(
                new MessageDeliveryStatusDto(status),
                HttpStatus.OK
        );
    }
    
    public static class SendMessageDto {
        public final String recipient, text;
        
        public SendMessageDto() {
            recipient = text = null;
        }
        
        public SendMessageDto(String recipient, String text) {
            this.recipient = recipient;
            this.text = text;
        }
    }
    
    public static class MessageDeliveryStatusDto {
        public final MessageDeliveryStatus status;
        
        public MessageDeliveryStatusDto(MessageDeliveryStatus status) {
            this.status = status;
        }
    }
}
