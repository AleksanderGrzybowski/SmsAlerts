package pl.kelog.smsalerts.kspoller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.kelog.smsalerts.ksdownloader.KsDownloaderService;
import pl.kelog.smsalerts.ksparser.KsInfoEntryDto;
import pl.kelog.smsalerts.sms.MessageService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static pl.kelog.smsalerts.kspoller.KsPollerServiceImpl.FORMATTER;

public class KsPollerServiceImplTest {
    
    private KsInfoEntryRepository repository;
    private KsDownloaderService downloaderService;
    private MessageService messageService;
    private String recipient = "+123";
    
    private KsPollerServiceImpl service;
    
    @Before
    public void setup() {
        this.repository = mock(KsInfoEntryRepository.class);
        this.downloaderService = mock(KsDownloaderService.class);
        this.messageService = mock(MessageService.class);
        this.service = new KsPollerServiceImpl(repository, downloaderService, messageService, recipient);
    }
    
    @Test
    public void given_empty_datastore_should_fetch_and_store_last_page_of_results() throws Exception {
        KsInfoEntryDto entry = new KsInfoEntryDto("Wypadek Ustroń Zdrój", LocalDateTime.now());
        KsInfoEntry entity = new KsInfoEntry(null, entry.title, entry.publishedDate.format(FORMATTER));
        
        when(repository.count()).thenReturn(0L);
        when(downloaderService.downloadFirstPage()).thenReturn(asList(entry));
        
        service.prefetchEntriesIfNonePresent();
        
        verify(repository, times(1)).save(entity);
    }
    
    @Test
    public void given_nonempty_datastore_should_not_fetch_and_not_fill_any_data() throws Exception {
        when(repository.count()).thenReturn(1L);
        
        service.prefetchEntriesIfNonePresent();
        
        verify(repository, never()).save(Mockito.any(KsInfoEntry.class));
    }
    
    @Test
    public void given_empty_datastore_and_empty_entries_download_should_do_nothing() {
        when(repository.countByPublishedDate("13-04-2017 21:02")).thenReturn(0);
        when(downloaderService.downloadFirstPage()).thenReturn(emptyList());
        
        service.pollAndSend("Katowice");
        
        verify(repository, never()).save(any(KsInfoEntry.class));
        
        verify(downloaderService, times(1)).downloadFirstPage();
        verify(messageService, never()).sendAndStore(anyString(), anyString());
    }
    
    @Test
    public void given_empty_datastore_should_download_all_entries_and_send_messages_on_matching_patterns() {
        List<KsInfoEntryDto> entries = asList(
                new KsInfoEntryDto("Wypadek Ustroń Zdrój", LocalDateTime.now()),
                new KsInfoEntryDto("Opóźnienie Katowice", LocalDateTime.now()),
                new KsInfoEntryDto("Roboty torowe na odcinku Pszczyna-Kobiór", LocalDateTime.now())
        );
        List<KsInfoEntry> entities = entries.stream().map(entry ->
                new KsInfoEntry(null, entry.title, entry.publishedDate.format(FORMATTER))
        ).collect(Collectors.toList());
        
        when(downloaderService.downloadFirstPage()).thenReturn(entries);
        
        service.pollAndSend("Katowice");
        
        verify(repository, times(1)).save(entities.get(0));
        verify(repository, times(1)).save(entities.get(1));
        verify(repository, times(1)).save(entities.get(2));
        verify(downloaderService, times(1)).downloadFirstPage();
        verify(messageService, times(1)).sendAndStore(recipient, "Opóźnienie Katowice");
    }
    
    @Test
    public void given_nonempty_datastore_should_download_all_entries_and_save_and_send_only_on_new_matching_entries() {
        List<KsInfoEntryDto> entries = asList(
                new KsInfoEntryDto("Wypadek Ustroń Zdrój", LocalDateTime.of(2017, Month.APRIL, 13, 21, 0)),
                new KsInfoEntryDto("Opóźnienie Katowice", LocalDateTime.of(2017, Month.APRIL, 13, 23, 0))
        );
        
        when(repository.countByPublishedDate(
                LocalDateTime.of(2017, Month.APRIL, 13, 21, 0).format(FORMATTER)
        )).thenReturn(1);
        when(repository.countByPublishedDate(
                LocalDateTime.of(2017, Month.APRIL, 13, 23, 0).format(FORMATTER)
        )).thenReturn(0);
        
        when(downloaderService.downloadFirstPage()).thenReturn(entries);
        
        service.pollAndSend("Katowice");
        
        verify(repository, times(1)).save(new KsInfoEntry(
                null,
                "Opóźnienie Katowice",
                LocalDateTime.of(2017, Month.APRIL, 13, 23, 0).format(FORMATTER)
        ));
        
        verify(downloaderService, times(1)).downloadFirstPage();
        verify(messageService, times(1)).sendAndStore(recipient, "Opóźnienie Katowice");
    }
    
}