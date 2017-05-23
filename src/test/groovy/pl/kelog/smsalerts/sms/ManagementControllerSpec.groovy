package pl.kelog.smsalerts.sms

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import pl.kelog.smsalerts.management.ManagementController
import pl.kelog.smsalerts.validation.ValidationExceptionHandlerController
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.core.Is.is
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ManagementControllerSpec extends Specification {
    
    MessageService messageService
    
    MockMvc mockMvc
    
    void setup() {
        messageService = Mock(MessageService)
        
        mockMvc = standaloneSetup(new ManagementController(messageService))
                .setControllerAdvice(new ValidationExceptionHandlerController())
                .build()
    }
    
    void 'should_list_all_messages'() {
        when:
        messageService.list() >> [new Message(1L, '+123', 'text', MessageDeliveryStatus.OK)]
        
        then:
        mockMvc.perform(MockMvcRequestBuilders.get('/management/messages'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$', hasSize(1)))
                .andExpect(jsonPath('$[0].id', is(1)))
                .andExpect(jsonPath('$[0].recipient', is('+123')))
                .andExpect(jsonPath('$[0].text', is('text')))
                .andExpect(jsonPath('$[0].status', is('OK')))
    }
}