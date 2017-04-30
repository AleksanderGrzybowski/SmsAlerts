package pl.kelog.smsalerts.validation;

public class FieldError {
    public final String field, message;
    
    FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "(" + field + " - " + message + ")";
    }
}
