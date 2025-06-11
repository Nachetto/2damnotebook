package com.example.reymortiz_ejercicio1.domain.errores;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
