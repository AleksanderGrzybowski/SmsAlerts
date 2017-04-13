package pl.kelog.smsalerts.sms;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.kelog.smsalerts.validation.ValidationException;
import pl.kelog.smsalerts.validation.ValidationExceptionHandlerController;

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
    public void should_try_to_send_message_given_valid_params() throws Exception {
        when(service.sendAndStore("+123", "text")).thenReturn(MessageDeliveryStatus.OK);
        
        MessageController.SendMessageDto dto = new MessageController.SendMessageDto("+123", "text");
        
        mockMvc.perform(post("/gateway")
                .content(gson.toJson(dto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
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
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("someField")))
                .andExpect(jsonPath("$.errors[0].message", is("someMessage")));
    }
}