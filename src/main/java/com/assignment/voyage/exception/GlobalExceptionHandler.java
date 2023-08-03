package com.assignment.voyage.exception;

import com.assignment.voyage.dto.ApiResultDto;
import java.util.concurrent.RejectedExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResultDto> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        ApiResultDto responseDto = new ApiResultDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                // HTTP body
                responseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ApiResultDto> notFoundUserExceptionHandler(NotFoundUserException ex) {
        ApiResultDto responseDto = new ApiResultDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                // HTTP body
                responseDto,
                // HTTP status code
                HttpStatus.NOT_FOUND
        );
    }
}