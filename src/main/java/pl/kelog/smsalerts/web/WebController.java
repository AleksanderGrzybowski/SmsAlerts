package pl.kelog.smsalerts.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kelog.smsalerts.poller.KsInfoEntry;
import pl.kelog.smsalerts.poller.KsInfoEntryService;
import pl.kelog.smsalerts.sms.Message;
import pl.kelog.smsalerts.sms.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class WebController {
    
    private final MessageService messageService;
    private final KsInfoEntryService entryService;
    
    @GetMapping("/data")
    public DataDto fetchAllData() {
        return new DataDto(entryService.list(), messageService.list());
    }
    
    @Data
    private static class DataDto {
        public final List<KsInfoEntry> entries;
        public final List<Message> messages;
    }
}
