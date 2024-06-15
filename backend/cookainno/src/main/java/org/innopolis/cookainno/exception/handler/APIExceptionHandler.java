package org.innopolis.cookainno.exception.handler;

import org.innopolis.cookainno.exception.EmailNotConfirmedException;
import org.innopolis.cookainno.exception.InvalidConfirmationCodeException;
import org.innopolis.cookainno.exception.UserAlreadyExistsException;
import org.innopolis.cookainno.exception.ValidationException;
import org.innopolis.cookainno.exception.model.APIErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<APIErrorResponse> handleValidationException(ValidationException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new APIErrorResponse(exception.getStatusCode(), exception));
    }

    @ExceptionHandler(value = InvalidConfirmationCodeException.class)
    public ResponseEntity<APIErrorResponse> handleNotFoundException(InvalidConfirmationCodeException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new APIErrorResponse(exception.getStatusCode(), exception));
    }

    @ExceptionHandler(value = EmailNotConfirmedException.class)
    public ResponseEntity<APIErrorResponse> handleConflictException(EmailNotConfirmedException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new APIErrorResponse(exception.getStatusCode(), exception));
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<APIErrorResponse> handleConflictException(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new APIErrorResponse(exception.getStatusCode(), exception));
    }
}
