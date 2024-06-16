package org.innopolis.cookainno.exception.handler;

import org.innopolis.cookainno.exception.*;
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
    public ResponseEntity<APIErrorResponse> handleEmailNotConfirmedException(EmailNotConfirmedException exception) {
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

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleConflictException(UserNotFoundException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new APIErrorResponse(exception.getStatusCode(), exception));
    }
}
