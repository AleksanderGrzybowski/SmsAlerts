package pl.kelog.smsalerts.management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.kelog.smsalerts.kspoller.KsPollerService;
import pl.kelog.smsalerts.sms.Message;
import pl.kelog.smsalerts.sms.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/management")
public class ManagementController {
    
    private final KsPollerService ksPollerService;
    private final MessageService messageService;
    
    public ManagementController(KsPollerService ksPollerService, MessageService messageService) {
        this.ksPollerService = ksPollerService;
        this.messageService = messageService;
    }
    
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ResponseEntity<List<ListMessageDto>> list() {
        return new ResponseEntity<>(
                messageService.list().stream().map(ListMessageDto::new).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }
    
    @RequestMapping(value = "/triggerPoll", method = RequestMethod.POST)
    public ResponseEntity<Void> triggerPoll() {
        ksPollerService.pollAndSend("Chybie");
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    private static class ListMessageDto {
        public final Long id;
        public final String recipient, text;
        public final String status;
        
        public ListMessageDto(Message message) {
            this.id = message.getId();
            this.recipient = message.getRecipient();
            this.text = message.getText();
            this.status = message.getStatus().toString();
        }
    }
}
