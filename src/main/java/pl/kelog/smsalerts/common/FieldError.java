package pl.kelog.smsalerts.common;

public class FieldError {
    public final String field, message;
    
    FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
