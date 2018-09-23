package pl.kelog.smsalerts.validation;

import lombok.Data;

@Data
class FieldError {
    public final String field, message;
}
