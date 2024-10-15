package com.hnm.userexpecienceservice.exception;

import com.hnm.userexpecienceservice.dto.response.ExceptionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestGlobalExceptionHandlerTest {

    @InjectMocks
    private RestGlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new RestGlobalExceptionHandler();
    }

    @Test
    void testUserNotFoundExceptionHandler() {

        UserNotFoundException userNotFoundException = new UserNotFoundException(1L);

        ResponseEntity<ExceptionDTO> response = exceptionHandler.userNotFound(userNotFoundException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("User id 1 not found", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getBody().getStatus());
    }
}