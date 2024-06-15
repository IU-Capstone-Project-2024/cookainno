package org.innopolis.cookainno.exception;

import lombok.Getter;

@Getter
public class EmailNotConfirmedException extends RuntimeException {
    private static final int STATUS_CODE = 403;

    public EmailNotConfirmedException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}

