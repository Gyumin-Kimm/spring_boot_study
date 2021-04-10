package com.example.study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//204 중복 충돌 status code
@ResponseStatus(code = HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException{
    public NoContentException(String message) {
        super(message);
    }
}
