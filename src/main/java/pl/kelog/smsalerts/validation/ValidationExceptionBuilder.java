package pl.kelog.smsalerts.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationExceptionBuilder {
    
    private List<FieldError> errors = new ArrayList<>();
    
    public ValidationExceptionBuilder add(String field, String message) {
        errors.add(new FieldError(field, message));
        return this;
    }
    
    public ValidationExceptionBuilder add(boolean isValid, String field, String message) {
        if (!isValid) {
            add(field, message);
        }
        return this;
    }
    
    public void throwIfErrors() {
        if (errors.size() > 0) {
            throw build();
        }
    }
    
    public ValidationException build() {
        return new ValidationException(errors);
    }
}
