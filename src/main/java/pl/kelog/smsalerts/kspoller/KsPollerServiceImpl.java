package pl.kelog.smsalerts.kspoller;

import pl.kelog.smsalerts.ksdownloader.KsDownloaderService;
import pl.kelog.smsalerts.ksparser.KsInfoEntryDto;
import pl.kelog.smsalerts.sms.MessageService;

import java.time.format.DateTimeFormatter;

class KsPollerServiceImpl implements KsPollerService {
    
    private final KsInfoEntryRepository repository;
    private final KsDownloaderService downloaderService;
    private final MessageService messageService;
    private final String recipient;
    
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    
    public KsPollerServiceImpl(
            KsInfoEntryRepository repository,
            KsDownloaderService downloaderService,
            MessageService messageService,
            String recipient
    ) {
        this.repository = repository;
        this.downloaderService = downloaderService;
        this.messageService = messageService;
        this.recipient = recipient;
    }
    
    @Override
    public void pollAndSend(String pattern) {
        for (KsInfoEntryDto entryDto : downloaderService.downloadFirstPage()) {
            if (repository.countByPublishedDate(entryDto.publishedDate.format(FORMATTER)) == 0) {
                // save it
                repository.save(new KsInfoEntry(entryDto.title, entryDto.publishedDate.format(FORMATTER)));
                
                if (entryDto.title.contains(pattern)) {
                    messageService.sendAndStore(recipient, entryDto.title);
                }
            }
        }
    }
}
