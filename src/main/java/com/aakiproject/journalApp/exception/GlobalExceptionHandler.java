package com.aakiproject.journalApp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Invalid Json / Extra fields
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid request body: contains unknown or malformed fields");
    }

    // 409 – Duplicate resource
    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<String> handleConflict(DuplicateEntryException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate entry found");
    }

    // 422 – Validation / business rule failure
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleUnprocessable(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    // 500 – Server error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerError() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error");
    }

}