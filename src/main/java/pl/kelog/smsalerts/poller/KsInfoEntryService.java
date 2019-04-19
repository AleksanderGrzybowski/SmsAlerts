package pl.kelog.smsalerts.poller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KsInfoEntryService {
    
    private final KsInfoEntryRepository repository;
    
    public KsInfoEntry findById(long id) {
        return repository.findOne(id);
    }
    
    public List<KsInfoEntry> list() {
        return repository.findAll();
    }
}
