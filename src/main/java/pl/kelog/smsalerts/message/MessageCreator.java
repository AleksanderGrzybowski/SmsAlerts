package pl.kelog.smsalerts.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kelog.smsalerts.poller.KsInfoEntry;

@Service
@RequiredArgsConstructor
public class MessageCreator {
    
    static final int MESSAGE_LENGTH_LIMIT = 160;
    private static final String SEPARATOR = " - ";
    
    private final String baseUrl;
    
    public String createMessage(KsInfoEntry entry) {
        return shortenTitleIfNeeded(entry) + SEPARATOR + getUrl(entry);
    }
    
    private String getUrl(KsInfoEntry entry) {
        return baseUrl + "/r/" + entry.getId();
    }
    
    private String shortenTitleIfNeeded(KsInfoEntry entry) {
        String title = entry.getTitle();
        int sumLength = String.join(title, SEPARATOR, getUrl(entry)).length();
        
        if (sumLength > MESSAGE_LENGTH_LIMIT) {
            return title.substring(0, title.length() - (sumLength - MESSAGE_LENGTH_LIMIT));
        } else {
            return title;
        }
    }
}
