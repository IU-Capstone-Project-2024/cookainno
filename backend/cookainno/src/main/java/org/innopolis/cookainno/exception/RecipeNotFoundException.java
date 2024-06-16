package org.innopolis.cookainno.exception;

import lombok.Getter;

@Getter
public class RecipeNotFoundException extends RuntimeException {
    private static final int STATUS_CODE = 404;

    public RecipeNotFoundException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}