package com.hnm.userexpecienceservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionDTO {

    private HttpStatus status;
    private String message;

}
