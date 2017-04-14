package pl.kelog.smsalerts.sms;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kelog.smsalerts.management.ManagementController;
import pl.kelog.smsalerts.validation.ValidationExceptionHandlerController;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ManagementControllerTest {
    
    private MessageService messageService;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        messageService = mock(MessageService.class);
        
        mockMvc = standaloneSetup(new ManagementController(messageService))
                .setControllerAdvice(new ValidationExceptionHandlerController())
                .build();
    }
    
    @Test
    public void should_list_all_messages() throws Exception {
        when(messageService.list()).thenReturn(asList(new Message(1L, "+123", "text", MessageDeliveryStatus.OK)));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/management/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].recipient", is("+123")))
                .andExpect(jsonPath("$[0].text", is("text")))
                .andExpect(jsonPath("$[0].status", is("OK")));
    }
}