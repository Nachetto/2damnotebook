package com.example.reyortiz_ejercicio3.domain.errores;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
