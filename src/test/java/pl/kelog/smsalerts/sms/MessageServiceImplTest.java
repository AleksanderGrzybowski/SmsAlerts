package pl.kelog.smsalerts.sms;

import org.junit.Before;
import org.junit.Test;
import pl.kelog.smsalerts.gateway.GatewayService;
import pl.kelog.smsalerts.validation.ValidationException;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageServiceImplTest {
    
    private MessageService service;
    
    private GatewayService gatewayService;
    private MessageRepository repository;
    
    @Before
    public void setup() {
        gatewayService = mock(GatewayService.class);
        repository = mock(MessageRepository.class);
        service = new MessageServiceImpl(gatewayService, repository);
    }
    
    @Test
    public void should_send_message_and_store_it_and_return_status() {
        Message sent = new Message(null, "+123", "text", MessageDeliveryStatus.OK);
        when(gatewayService.send("+123", "text")).thenReturn(MessageDeliveryStatus.OK);
        when(repository.save(sent)).thenReturn(sent);
        
        MessageDeliveryStatus status = service.sendAndStore("+123", "text");
        
        assertThat(status).isEqualTo(MessageDeliveryStatus.OK);
        verify(gatewayService, times(1)).send("+123", "text");
        verify(repository, times(1)).save(sent);
    }
    
    @Test
    public void should_list_all_messages() {
        List<Message> expected = asList(new Message(1L, "+123", "text", MessageDeliveryStatus.OK));
        when(repository.findAll()).thenReturn(expected);
        
        List<Message> result = service.list();
        
        assertThat(result).isEqualTo(expected);
        verify(repository, atLeastOnce()).findAll();
    }
    
    @Test(expected = ValidationException.class)
    public void should_reject_empty_recipient() {
        service.sendAndStore("", "text");
    }
    
    @Test(expected = ValidationException.class)
    public void should_reject_empty_text() {
        service.sendAndStore("+123", "");
    }
    
    @Test(expected = ValidationException.class)
    public void should_reject_text_longer_than_160_chars() {
        service.sendAndStore("+123", createStringOfLength(161));
    }
    
    private String createStringOfLength(int length) {
        return new String(new char[length]).replace('\0', 'X');
    }
}