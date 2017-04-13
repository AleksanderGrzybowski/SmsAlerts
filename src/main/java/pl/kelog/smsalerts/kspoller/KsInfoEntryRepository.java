package pl.kelog.smsalerts.kspoller;

import org.springframework.data.jpa.repository.JpaRepository;

interface KsInfoEntryRepository extends JpaRepository<KsInfoEntry, Long> {
    
    int countByPublishedDate(String publishedDate);
}
