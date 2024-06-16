package org.innopolis.cookainno.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private static final int STATUS_CODE = 404;

    public UserNotFoundException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}