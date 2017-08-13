package pl.kelog.smsalerts.kspoller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getPublishedDate() {
        return publishedDate;
    }
    
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        KsInfoEntry that = (KsInfoEntry) o;
        
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return publishedDate != null ? publishedDate.equals(that.publishedDate) : that.publishedDate == null;
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (publishedDate != null ? publishedDate.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "KsInfoEntry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                '}';
    }
}
