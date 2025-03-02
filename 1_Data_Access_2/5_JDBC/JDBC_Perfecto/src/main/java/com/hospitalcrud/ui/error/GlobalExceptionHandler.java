package com.hospitalcrud.ui.error;

import com.hospitalcrud.domain.error.DUPLICATED_USERNAME;
import com.hospitalcrud.domain.error.DataBaseError;
import com.hospitalcrud.domain.error.FOREIGN_KEY_ERROR;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DUPLICATED_USERNAME.class)
    public ResponseEntity<String> handleForeignKetException(DUPLICATED_USERNAME e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(FOREIGN_KEY_ERROR.class)
    public ResponseEntity<String> handleForeignKetException(FOREIGN_KEY_ERROR e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
