package pl.kelog.smsalerts.poller;

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
    
    private String detailsUrl;
    
    @SuppressWarnings("unused")
    public KsInfoEntry() {
    }
    
    KsInfoEntry(String title, String publishedDate, String detailsUrl) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.detailsUrl = detailsUrl;
    }
    
    public KsInfoEntry(Long id, String title, String publishedDate, String detailsUrl) {
        this(title, publishedDate, detailsUrl);
        this.id = id;
    }
}
