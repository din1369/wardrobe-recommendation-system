package com.hnm.userexpecienceservice.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserNotFoundExceptionTest {

    @Test
    void testUserNotFoundExceptionMessage() {
        UserNotFoundException exception = new UserNotFoundException(123L);
        assertEquals("User id 123 not found", exception.getMessage());
    }
}
