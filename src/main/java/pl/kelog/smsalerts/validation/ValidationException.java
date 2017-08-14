package pl.kelog.smsalerts.validation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {
    
    public final List<FieldError> errors;
    
    ValidationException(List<FieldError> errors) {
        super(stringifyErrors(errors));
        this.errors = Collections.unmodifiableList(errors);
    }
    
    public static ValidationExceptionBuilder builder() {
        return new ValidationExceptionBuilder();
    }
    
    private static String stringifyErrors(List<FieldError> errors) {
        return errors.stream().map(Object::toString).collect(Collectors.joining(","));
    }
}
