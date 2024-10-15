package com.example.reyortiz_ejercicio3.domain.errores;

public class UpdateException extends RuntimeException{
    public UpdateException(String message) {
        super(message);
    }
}
