package com.luminicel.client.config;


import com.luminicel.client.rest.Error;
import com.luminicel.client.rest.JSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            DataAccessException.class
    })
    public ResponseEntity<JSend<String>> handleNotFoundException(RuntimeException ex) {
        JSend<String> error = new Error<>(404,ex.getLocalizedMessage());
		return ResponseEntity
				.status(error.getCode())
				.body(error);
    }

}