package net.ivan.kavaliou.moneyman.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<Object> handleServiceException(ServiceException e){
        ApiException apiException = new ApiException(e.getMessage(), HttpStatus.FORBIDDEN, LocalDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
    }
}
