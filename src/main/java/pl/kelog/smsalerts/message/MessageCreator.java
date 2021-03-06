package pl.kelog.smsalerts.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kelog.smsalerts.poller.KsInfoEntry;

import static pl.kelog.smsalerts.web.DetailsRedirectController.REDIRECT_URL_PREFIX;

@Service
@RequiredArgsConstructor
public class MessageCreator {
    
    static final int MESSAGE_LENGTH_LIMIT = 160;
    private static final String SEPARATOR = " - ";
    
    private final String baseUrl;
    
    public String createMessage(KsInfoEntry entry) {
        return shortenTitleIfNeeded(entry) + SEPARATOR + createRedirectUrl(entry);
    }
    
    private String createRedirectUrl(KsInfoEntry entry) {
        return baseUrl + REDIRECT_URL_PREFIX + entry.getId();
    }
    
    private String shortenTitleIfNeeded(KsInfoEntry entry) {
        String title = entry.getTitle();
        int sumLength = String.join(title, SEPARATOR, createRedirectUrl(entry)).length();
        
        if (sumLength > MESSAGE_LENGTH_LIMIT) {
            return title.substring(0, title.length() - (sumLength - MESSAGE_LENGTH_LIMIT));
        } else {
            return title;
        }
    }
}
