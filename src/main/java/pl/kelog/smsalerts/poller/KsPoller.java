package pl.kelog.smsalerts.poller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.kelog.smsalerts.downloader.KsEntryDownloader;
import pl.kelog.smsalerts.dto.KsInfoEntryDto;
import pl.kelog.smsalerts.message.MessageCreator;
import pl.kelog.smsalerts.sms.MessageService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Log
@RequiredArgsConstructor
public class KsPoller {
    
    private final KsInfoEntryRepository repository;
    private final KsEntryDownloader downloaderService;
    private final MessageService messageService;
    private final MessageCreator messageCreator;
    private final String recipient;
    private final List<String> patterns;
    
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public void prefetchEntriesIfNonePresent() {
        if (repository.count() == 0) {
            log.info("There are no entries, storing all...");
            downloaderService.downloadFirstPage().forEach(this::store);
            log.info("First page of entries stored");
        } else {
            log.info("Some entries already exist, not filling datastore");
        }
    }
    
    public void pollAndSend() {
        log.info("Polling for new entries...");
        
        List<KsInfoEntryDto> entries = downloaderService.downloadFirstPage();
        entries.forEach(this::processMessage);
        
        log.info("Polling finished");
    }
    
    private void processMessage(KsInfoEntryDto entryDto) {
        String formattedDate = entryDto.publishedDate.format(FORMATTER);
        
        if (repository.countByPublishedDate(formattedDate) == 0) {
            log.info("New entry with date " + formattedDate + " - " + entryDto.title + " found, saving");
            KsInfoEntry newEntry = new KsInfoEntry(entryDto.title, formattedDate, entryDto.detailsUrl);
            repository.save(newEntry);
            
            if (shouldSendMessage(entryDto)) {
                log.info("Entry with date " + formattedDate + " matches patterns '" + patterns + "', sending message");
                messageService.sendAndStore(recipient, messageCreator.createMessage(newEntry));
            }
        }
    }
    
    boolean shouldSendMessage(KsInfoEntryDto entryDto) {
        return patterns.stream().map(String::toLowerCase).anyMatch(pattern -> {
                    String normalizedTitle = entryDto.title.toLowerCase();
                    return normalizedTitle.contains(pattern) || normalizedTitle.matches(pattern);
                }
        );
    }
    
    private void store(KsInfoEntryDto entry) {
        log.info("Storing entry " + entry.publishedDate.format(FORMATTER));
        repository.save(new KsInfoEntry(entry.title, entry.publishedDate.format(FORMATTER), entry.detailsUrl));
    }
}
