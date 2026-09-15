package com.posts.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), List.of(exception.getMessage()), exception.getClass().getSimpleName()));
    }


    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<ErrorResponse> handleEntityExistsException(final EntityExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), List.of(exception.getMessage()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                exception.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()), exception.getClass().getSimpleName()));
    }
}
