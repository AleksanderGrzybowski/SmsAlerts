package pl.kelog.smsalerts.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.kelog.smsalerts.poller.KsInfoEntry;
import pl.kelog.smsalerts.poller.KsInfoEntryService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
class DetailsRedirectController {
    
    private final KsInfoEntryService entryService;
    
    @RequestMapping(value = "/r/{id}", method = RequestMethod.GET)
    public ResponseEntity<Void> redirect(@PathVariable("id") int id, HttpServletResponse response) {
        Optional<KsInfoEntry> entry = entryService.list().stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        
        if (entry.isPresent()) {
            response.setHeader("Location", entry.get().getDetailsUrl());
            return new ResponseEntity<>(HttpStatus.TEMPORARY_REDIRECT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
