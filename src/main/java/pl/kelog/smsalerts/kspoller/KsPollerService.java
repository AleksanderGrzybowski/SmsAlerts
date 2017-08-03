package pl.kelog.smsalerts.kspoller;

import java.util.List;

public interface KsPollerService {
    void pollAndSend(List<String> pattern);
    
    void prefetchEntriesIfNonePresent();
}
