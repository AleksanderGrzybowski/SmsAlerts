package pl.kelog.smsalerts.poller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KsInfoEntryService {
    
    private final KsInfoEntryRepository repository;
    
    public KsInfoEntry findById(long id) {
        return repository.findOne(id);
    }
    
    public Page<KsInfoEntry> listWithPaging(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
