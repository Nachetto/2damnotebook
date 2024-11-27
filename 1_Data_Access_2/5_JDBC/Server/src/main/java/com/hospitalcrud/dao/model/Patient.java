package com.hospitalcrud.dao.model;

import com.hospitalcrud.domain.model.PatientUI;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class Patient {
    private int id;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private Credential credential;

    public Patient(int id, String name, LocalDate dob, String phone) {
        this.id = id;
        this.name = name;
        this.birthDate = dob;
        this.phone = phone;
    }
    public Patient(int id, String name, LocalDate dob, String phone, Credential credential) {
        this.id = id;
        this.name = name;
        this.birthDate = dob;
        this.phone = phone;
        this.credential = credential;
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" +
                birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + ";" + phone;
    }

    public PatientUI toPatientUI() {
        return new PatientUI(id, name, birthDate, phone, 1, "admin", "admin");
    }
}
