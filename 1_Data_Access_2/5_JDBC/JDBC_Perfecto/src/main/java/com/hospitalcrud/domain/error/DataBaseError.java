package com.hospitalcrud.domain.error;

public class DataBaseError extends RuntimeException{
    public DataBaseError(String message){
        super(message);
    }
}
