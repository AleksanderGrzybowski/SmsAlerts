package pl.kelog.smsalerts.common;

import java.util.List;

class ValidationExceptionDto {
    public final List<FieldError> errors;

    ValidationExceptionDto(ValidationException e) {
        this.errors = e.errors;
    }
}
