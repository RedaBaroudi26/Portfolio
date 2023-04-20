package com.smaaaak.Portfolio.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@RequiredArgsConstructor
public class ApiException {

    private final String message ;
    private final HttpStatus httpStatus ;
    private final ZonedDateTime timestamp ;

}
