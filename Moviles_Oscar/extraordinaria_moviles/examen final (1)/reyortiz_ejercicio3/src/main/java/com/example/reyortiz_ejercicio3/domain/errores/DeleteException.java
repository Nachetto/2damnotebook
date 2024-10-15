package com.example.reyortiz_ejercicio3.domain.errores;

public class DeleteException extends RuntimeException{
    public DeleteException(String message) {
        super(message);
    }
}
