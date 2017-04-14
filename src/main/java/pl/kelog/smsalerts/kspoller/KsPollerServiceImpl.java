package pl.kelog.smsalerts.kspoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kelog.smsalerts.ksdownloader.KsDownloaderService;
import pl.kelog.smsalerts.ksparser.KsInfoEntryDto;
import pl.kelog.smsalerts.sms.MessageService;

import java.time.format.DateTimeFormatter;
import java.util.List;

class KsPollerServiceImpl implements KsPollerService {
    
    private final KsInfoEntryRepository repository;
    private final KsDownloaderService downloaderService;
    private final MessageService messageService;
    private final String recipient;
    private final Logger log = LoggerFactory.getLogger(KsPollerServiceImpl.class);
    
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    
    public KsPollerServiceImpl(
            KsInfoEntryRepository repository,
            KsDownloaderService downloaderService,
            MessageService messageService,
            String recipient) {
        this.repository = repository;
        this.downloaderService = downloaderService;
        this.messageService = messageService;
        this.recipient = recipient;
    }
    
    @Override
    public void fillIfEmpty() {
        if (repository.count() == 0) {
            log.info("There are no entries, storing all...");
            downloaderService.downloadFirstPage().forEach(entry ->
                repository.save(new KsInfoEntry(entry.title, entry.publishedDate.format(FORMATTER)))
            );
            log.info("First page of entries stored.");
        }
    }
    
    @Override
    public void pollAndSend(String pattern) {
        log.info("Polling for new entries...");
        List<KsInfoEntryDto> entries = downloaderService.downloadFirstPage();
        for (KsInfoEntryDto entryDto : entries) {
            String formattedDate = entryDto.publishedDate.format(FORMATTER);
            
            if (repository.countByPublishedDate(formattedDate) == 0) {
                repository.save(new KsInfoEntry(entryDto.title, formattedDate));
                
                if (entryDto.title.contains(pattern)) {
                    messageService.sendAndStore(recipient, entryDto.title);
                }
            }
        }
        log.info("Polling finished.");
    }
}
