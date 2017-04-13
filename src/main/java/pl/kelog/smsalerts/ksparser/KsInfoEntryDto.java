package pl.kelog.smsalerts.ksparser;

import java.time.LocalDateTime;

public class KsInfoEntryDto {
    public final String title;
    public final LocalDateTime publishedDate;
    
    public KsInfoEntryDto(String title, LocalDateTime publishedDate) {
        this.title = title;
        this.publishedDate = publishedDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        KsInfoEntryDto that = (KsInfoEntryDto) o;
        
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return publishedDate != null ? publishedDate.equals(that.publishedDate) : that.publishedDate == null;
    }
    
    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (publishedDate != null ? publishedDate.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "KsInfoEntryDto{" +
                "title='" + title + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }
}
