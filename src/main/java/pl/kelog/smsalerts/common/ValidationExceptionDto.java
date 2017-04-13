package pl.kelog.smsalerts.common;

import java.util.List;

class ValidationExceptionDto {
    private final List<FieldError> errors;

    ValidationExceptionDto(ValidationException e) {
        this.errors = e.errors;
    }
}
