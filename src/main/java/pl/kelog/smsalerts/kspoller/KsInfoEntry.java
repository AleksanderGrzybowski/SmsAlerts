package pl.kelog.smsalerts.kspoller;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class KsInfoEntry {
    @Id
    @GeneratedValue
    private Long id;
    
    private String title;
    
    private String publishedDate;
    
    @SuppressWarnings("unused")
    public KsInfoEntry() {
    }
    
    KsInfoEntry(String title, String publishedDate) {
        this.title = title;
        this.publishedDate = publishedDate;
    }
    
    public KsInfoEntry(Long id, String title, String publishedDate) {
        this(title, publishedDate);
        this.id = id;
    }
}
