package pl.kelog.smsalerts.kspoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/polling")
public class KsPollerController {
    
    private final KsPollerService service;
    
    public KsPollerController(KsPollerService service) {
        this.service = service;
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Void> triggerPoll() {
        service.pollAndSend("Chybie");
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
