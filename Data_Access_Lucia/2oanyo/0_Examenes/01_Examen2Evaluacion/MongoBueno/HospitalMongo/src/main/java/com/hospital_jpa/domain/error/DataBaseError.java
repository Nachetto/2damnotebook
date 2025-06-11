package com.hospital_jpa.domain.error;

public class DataBaseError extends RuntimeException{
    public DataBaseError(String message){
        super(message);
    }
}
