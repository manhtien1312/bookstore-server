package com.example.bookstore.exception;

import com.example.bookstore.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotValidException {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<MessageResponse> handleNotValidException(MethodArgumentNotValidException exception){
        String message = exception.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
    }

}
