package org.example.paymentservice.exception;


import org.example.paymentservice.model.dto.responsedto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(NotFoundException ex)
    {
        ErrorResponseDto er = new ErrorResponseDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex)
    {
        ErrorResponseDto er = new ErrorResponseDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
    }
}
