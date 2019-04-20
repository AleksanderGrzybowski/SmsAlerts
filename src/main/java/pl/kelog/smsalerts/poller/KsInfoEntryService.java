package pl.kelog.smsalerts.poller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KsInfoEntryService {
    
    private static final Sort SORT = new Sort(Direction.DESC, "publishedDate", "scrapeTime");
    private final KsInfoEntryRepository repository;
    
    public KsInfoEntry findById(long id) {
        return repository.findOne(id);
    }
    
    public List<KsInfoEntry> list() {
        return repository.findAll(SORT);
    }
}
