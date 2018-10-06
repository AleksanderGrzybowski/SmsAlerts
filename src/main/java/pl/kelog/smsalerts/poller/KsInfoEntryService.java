package pl.kelog.smsalerts.poller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class KsInfoEntryService {
    
    private final KsInfoEntryRepository repository;
    
    public List<KsInfoEntry> list() {
        return repository.findAll().stream()
                .sorted(comparing(KsInfoEntry::getPublishedDate).reversed())
                .collect(toList());
    }
    
    public Page<KsInfoEntry> listWithPaging(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
