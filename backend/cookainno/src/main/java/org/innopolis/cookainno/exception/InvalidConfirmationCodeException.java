package org.innopolis.cookainno.exception;

import lombok.Getter;

@Getter
public class InvalidConfirmationCodeException extends RuntimeException {
    private static final int STATUS_CODE = 400;

    public InvalidConfirmationCodeException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}

