package org.innopolis.cookainno.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class ValidationException extends RuntimeException {
    private static final int STATUS_CODE = 400;

    public ValidationException(FieldError error) {
        super(error.getDefaultMessage());
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}
