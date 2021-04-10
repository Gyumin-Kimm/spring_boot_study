package com.example.study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//409 중복 충돌 status code
@ResponseStatus(code = HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException{
        public AlreadyExistsException(String message) {
            super(message);
        }
}

