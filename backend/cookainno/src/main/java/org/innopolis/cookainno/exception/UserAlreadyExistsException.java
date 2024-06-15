package org.innopolis.cookainno.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private static final int STATUS_CODE = 409;

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}