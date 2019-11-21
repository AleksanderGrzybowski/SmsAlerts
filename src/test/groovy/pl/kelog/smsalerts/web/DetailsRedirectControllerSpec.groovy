package pl.kelog.smsalerts.web

import org.springframework.test.web.servlet.MockMvc
import pl.kelog.smsalerts.poller.KsInfoEntry
import pl.kelog.smsalerts.poller.KsInfoEntryService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import static pl.kelog.smsalerts.web.DetailsRedirectController.REDIRECT_URL_PREFIX 

class DetailsRedirectControllerSpec extends Specification {

    MockMvc mockMvc

    KsInfoEntryService ksInfoEntryServiceStub = Mock(KsInfoEntryService)
    KsInfoEntry entry = new KsInfoEntry(123, '', '', 'http://detailsUrl')

    void setup() {
        mockMvc = standaloneSetup(new DetailsRedirectController(ksInfoEntryServiceStub)).build()
        ksInfoEntryServiceStub.findById(123) >> entry
    }

    def 'should redirect correctly for existing entry id'() {
        expect:
        mockMvc.perform(get("${REDIRECT_URL_PREFIX}${entry.id}"))
                .andExpect(status().isTemporaryRedirect())
                .andExpect(header().string('Location', entry.detailsUrl))
    }

    def 'should fail with 404 for nonexisting entry id'() {
        expect:
        mockMvc.perform(get("${REDIRECT_URL_PREFIX}${entry.id + 1}"))
                .andExpect(status().isNotFound())
    }
}
