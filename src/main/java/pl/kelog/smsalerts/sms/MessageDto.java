package pl.kelog.smsalerts.sms;

import lombok.Data;
import pl.kelog.smsalerts.dto.MessageDeliveryStatus;

@Data
public class MessageDto {
    public final Long id;
    
    public final String text;
    
    public final MessageDeliveryStatus status;
}
