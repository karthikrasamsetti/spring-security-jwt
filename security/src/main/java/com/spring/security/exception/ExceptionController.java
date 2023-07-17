package com.spring.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController{
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object>exception(UserNotFoundException exception){
        log.info("Enter into UserNotFound Exception");
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    }
}
