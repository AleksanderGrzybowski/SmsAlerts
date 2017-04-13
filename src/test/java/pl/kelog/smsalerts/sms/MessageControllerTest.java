package pl.kelog.smsalerts.sms;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kelog.smsalerts.validation.ValidationException;
import pl.kelog.smsalerts.validation.ValidationExceptionHandlerController;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MessageControllerTest {
    
    private MessageService service;
    private MockMvc mockMvc;
    private Gson gson = new Gson();
    
    @Before
    public void setup() {
        service = mock(MessageService.class);
        mockMvc = standaloneSetup(new MessageController(service))
                .setControllerAdvice(new ValidationExceptionHandlerController())
                .build();
    }
    
    @Test
    public void should_list_all_messages() throws Exception {
        when(service.list()).thenReturn(asList(new Message(1L, "+123", "text", MessageDeliveryStatus.OK)));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/gateway"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].recipient", is("+123")))
                .andExpect(jsonPath("$[0].text", is("text")))
                .andExpect(jsonPath("$[0].status", is("OK")));
    }
    
    @Test
    public void should_try_to_send_message_given_valid_params() throws Exception {
        when(service.sendAndStore("+123", "text")).thenReturn(MessageDeliveryStatus.OK);
        
        MessageController.SendMessageDto dto = new MessageController.SendMessageDto("+123", "text");
        
        mockMvc.perform(post("/gateway")
                .content(gson.toJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")));
    }
    
    @Test
    public void should_fail_to_send_message_given_invalid_params() throws Exception {
        when(service.sendAndStore("+123", "text")).thenThrow(
                ValidationException.builder().add("someField", "someMessage").build()
        );
        
        MessageController.SendMessageDto dto = new MessageController.SendMessageDto("+123", "text");
        
        mockMvc.perform(post("/gateway")
                .content(gson.toJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("someField")))
                .andExpect(jsonPath("$.errors[0].message", is("someMessage")));
    }
}