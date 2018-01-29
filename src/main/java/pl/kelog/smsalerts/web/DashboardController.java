package pl.kelog.smsalerts.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.kelog.smsalerts.kspoller.KsInfoEntryService;
import pl.kelog.smsalerts.sms.MessageService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
class DashboardController extends WebMvcConfigurerAdapter {
    
    private final MessageService messageService;
    private final KsInfoEntryService entryService;
    
    @GetMapping("/")
    public String dashboard(Map<String, Object> model) {
        model.put("entries", entryService.list());
        model.put("messages", messageService.list());
        return "dashboard";
    }
}
