package com.example.demo.common;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int code = HttpStatus.BAD_REQUEST.value();
    private Object error;
    
    public ErrorResponse(int code, Object error) {
        this.code = code;
        this.error = error;
    }
}
