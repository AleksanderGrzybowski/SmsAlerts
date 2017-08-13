package pl.kelog.smsalerts.validation;

import java.util.List;

class ValidationExceptionDto {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final List<FieldError> errors;

    ValidationExceptionDto(ValidationException e) {
        this.errors = e.errors;
    }
}
