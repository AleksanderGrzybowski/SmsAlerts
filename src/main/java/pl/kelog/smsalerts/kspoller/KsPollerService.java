package pl.kelog.smsalerts.kspoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.kelog.smsalerts.ksdownloader.KsDownloaderService;
import pl.kelog.smsalerts.ksparser.KsInfoEntryDto;
import pl.kelog.smsalerts.sms.MessageService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Log
@RequiredArgsConstructor
public class KsPollerService {
    
    private final KsInfoEntryRepository repository;
    private final KsDownloaderService downloaderService;
    private final MessageService messageService;
    private final String recipient;
    
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    
    public void prefetchEntriesIfNonePresent() {
        if (repository.count() == 0) {
            log.info("There are no entries, storing all...");
            downloaderService.downloadFirstPage().forEach(entry -> {
                        log.info("Storing entry " + entry.publishedDate.format(FORMATTER));
                        repository.save(new KsInfoEntry(entry.title, entry.publishedDate.format(FORMATTER)));
                    }
            );
            log.info("First page of entries stored");
        } else {
            log.info("Some entries already exist, not filling datastore");
        }
    }
    
    public void pollAndSend(List<String> patterns) {
        log.info("Polling for new entries...");
        List<KsInfoEntryDto> entries = downloaderService.downloadFirstPage();
        
        for (KsInfoEntryDto entryDto : entries) {
            String formattedDate = entryDto.publishedDate.format(FORMATTER);
            
            if (repository.countByPublishedDate(formattedDate) == 0) {
                log.info("New entry with date " + formattedDate + " - " + entryDto.title + " found, saving");
                repository.save(new KsInfoEntry(entryDto.title, formattedDate));
                
                if (shouldSendMessage(patterns, entryDto)) {
                    log.info("Entry with date " + formattedDate + " matches patterns '" + patterns + "', sending message");
                    messageService.sendAndStore(recipient, entryDto.title);
                }
            }
        }
        
        log.info("Polling finished");
    }
    
    public boolean shouldSendMessage(List<String> patterns, KsInfoEntryDto entryDto) {
        return patterns.stream().anyMatch(pattern -> 
                entryDto.title.contains(pattern) || entryDto.title.matches(pattern)
        );
    }
}
