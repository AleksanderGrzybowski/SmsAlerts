package pl.kelog.smsalerts.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kelog.smsalerts.gateway.SmsGateway;
import pl.kelog.smsalerts.poller.KsInfoEntry;
import pl.kelog.smsalerts.poller.KsInfoEntryService;
import pl.kelog.smsalerts.sms.MessageDto;
import pl.kelog.smsalerts.sms.MessageService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class WebController {
    
    private final MessageService messageService;
    private final KsInfoEntryService entryService;
    private final SmsGateway smsGateway;
    
    @GetMapping("/alerts")
    public Page<KsInfoEntry> listEntries(@PageableDefault(
            size = 5,
            sort = {"publishedDate", "scrapeTime"},
            direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return entryService.listWithPaging(pageable);
    }
    
    @GetMapping("/messages")
    public List<MessageDto> listMessages() {
        return messageService.list();
    }
    
    @GetMapping("/balance")
    public AccountBalanceDto accountBalance() {
        return new AccountBalanceDto(smsGateway.accountBalance());
    }
    
    @Data
    private static class AccountBalanceDto {
        public final BigDecimal accountBalance;
    }
}
