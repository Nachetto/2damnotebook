package com.example.reyortiz_ejercicio3.domain.errores;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ExceptionHandlers {


    @ExceptionHandler(UpdateException.class)
    public ResponseEntity<ApiError> handleException(UpdateException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<ApiError> handleException(DeleteException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ApiError> handleException(TokenException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleException(AuthenticationException e) {
        ApiError apiError = new ApiError(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(apiError);
    }

}
