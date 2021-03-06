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

@RestController
@RequiredArgsConstructor
public class DetailsRedirectController {
    
    private final KsInfoEntryService entryService;
    
    public static final String REDIRECT_URL_PREFIX = "/r/";
    
    @RequestMapping(value = REDIRECT_URL_PREFIX + "{id}", method = RequestMethod.GET)
    public ResponseEntity<Void> redirect(
            @SuppressWarnings("MVCPathVariableInspection") @PathVariable("id") int id,
            HttpServletResponse response
    ) {
        KsInfoEntry entry = entryService.findById(id);
        
        if (entry != null) {
            response.setHeader("Location", entry.getDetailsUrl());
            return new ResponseEntity<>(HttpStatus.TEMPORARY_REDIRECT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
