package com.luminicel.tenant.config;

import com.luminicel.tenant.tenant.domain.exception.TenantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlingController {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
//    }

//    @ExceptionHandler(TenantNotFoundException.class)
//    public ResponseEntity<String> handleNotFoundException(TenantNotFoundException ex) {
//        var pd = ProblemDetail
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }
//
    @ExceptionHandler
    ProblemDetail handle(IllegalArgumentException exception){
        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exception.getLocalizedMessage());
        return problemDetail;
    }
}
