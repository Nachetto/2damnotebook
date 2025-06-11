package com.hospital_jpa.ui.error;

import com.hospital_jpa.domain.error.DUPLICATED_USERNAME;
import com.hospital_jpa.domain.error.Error_Creating_Object;
import com.hospital_jpa.domain.error.FOREIGN_KEY_ERROR;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DUPLICATED_USERNAME.class)
    public ResponseEntity<String> handleDuplicatedUsernameException(DUPLICATED_USERNAME e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(FOREIGN_KEY_ERROR.class)
    public ResponseEntity<String> handleForeignKeyException(FOREIGN_KEY_ERROR e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(Error_Creating_Object.class)
    public ResponseEntity<String> handleErrorCreatingObjectException(Error_Creating_Object e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
