package pl.kelog.smsalerts.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kelog.smsalerts.kspoller.KsInfoEntryService;
import pl.kelog.smsalerts.sms.MessageService;

import static java.util.stream.Collectors.joining;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
class InfoController {
    
    private final MessageService messageService;
    private final KsInfoEntryService entryService;
    
    private static final int TITLE_DASHES_COUNT = 10;
    
    @RequestMapping(produces = "text/plain")
    public String plainTextAnnotation() {
        return buildView();
    }
    
    private String buildView() {
        return title("Entries")
                + entryService.listNewestFirst().stream()
                .map(entry -> entry.getPublishedDate() + " " + entry.getTitle())
                .collect(joining("\n"))
                
                + "\n\n"
                + title("Sent messages")
                + messageService.list().stream()
                .map(message -> message.getRecipient() + " " + message.getStatus() + " " + message.getText())
                .collect(joining("\n"));
    }
    
    private String title(String text) {
        return dashes() + " " + text + " " + dashes() + "\n";
    }
    
    // https://stackoverflow.com/questions/2255500/can-i-multiply-strings-in-java-to-repeat-sequences
    private String dashes() {
        return new String(new char[TITLE_DASHES_COUNT]).replace("\0", "-");
    }
}
