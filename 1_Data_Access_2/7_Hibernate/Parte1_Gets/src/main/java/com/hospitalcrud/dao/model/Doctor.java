package com.hospitalcrud.dao.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Doctor {
    private int id;
    private String name;
    private String specialty;

    public Doctor(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }
}