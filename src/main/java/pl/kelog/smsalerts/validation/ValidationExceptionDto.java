package pl.kelog.smsalerts.validation;

import java.util.List;

public class ValidationExceptionDto {
    public final List<FieldError> errors;

    ValidationExceptionDto(ValidationException e) {
        this.errors = e.errors;
    }
}
