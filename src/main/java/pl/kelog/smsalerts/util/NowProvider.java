package pl.kelog.smsalerts.util;

import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class NowProvider {
    
    public LocalTime now() {
        return LocalTime.now();
    }
}
