package com.smaaaak.Portfolio.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value={ApiRequestException.class })
    public ResponseEntity<Object> handlerApiRequestException(ApiRequestException e){
       HttpStatus badRequest = HttpStatus.BAD_REQUEST ;
       ApiException apiException = new ApiException(e.getMessage() , badRequest , ZonedDateTime.now(ZoneId.of("Z"))) ;
       return new ResponseEntity<>(apiException , badRequest) ;
    }

    @ExceptionHandler(value={UserNotFoundException.class})
    public ResponseEntity<Object> handlerUserNotFound(UserNotFoundException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST ;
        ApiException apiException = new ApiException(e.getMessage() , badRequest , ZonedDateTime.now(ZoneId.of("Z"))) ;
        return new ResponseEntity<>(apiException , badRequest) ;
    }


}
