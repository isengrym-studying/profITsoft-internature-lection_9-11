package ua.klieshchunov.lection911.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.klieshchunov.lection911.service.exception.BookAlreadyExistsException;
import ua.klieshchunov.lection911.service.exception.BookNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookAlreadyExistsException.class)
    protected ResponseEntity<Object> handleBookAlreadyExistsException(BookAlreadyExistsException e) {
        log.warn("BookAlreadyExistsException thrown: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException e) {
        log.warn("BookNotFoundException thrown: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    private static ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponse response = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), message);
        return ResponseEntity.status(httpStatus.value()).body(response);
    }

    @Getter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class ErrorResponse {
        private int status;
        private String error;
        private String message;
    }
}
