package com.hnm.userexpecienceservice.exception;

import com.hnm.userexpecienceservice.dto.response.ExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestGlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDTO> userNotFound(UserNotFoundException e) {
        ExceptionDTO error = new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        log.error(error.getMessage(), e);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> propertyValueException(MethodArgumentNotValidException e) {
        ExceptionDTO error = new ExceptionDTO(HttpStatus.BAD_REQUEST, ">> Incorrect request");
        log.error(error.getMessage(), e);
        return new ResponseEntity<>(error, error.getStatus());
    }
}
