package pl.kelog.smsalerts.kspoller;

public interface KsPollerService {
    void pollAndSend(String pattern);
}
