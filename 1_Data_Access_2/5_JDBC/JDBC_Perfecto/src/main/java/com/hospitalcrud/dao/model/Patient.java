package com.hospitalcrud.dao.model;

import com.hospitalcrud.common.Constantes;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Patient {
    private int id;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private Credential credential;
    private int paid;













    public Patient(int id, String name, LocalDate birthDate, String phone) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
    }

    public Patient(int id, String name, LocalDate birthDate, String phone, Credential credential) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.credential = credential;
    }

    public Patient(int id, String name, LocalDate birthDate, String phone, int paid) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.paid = paid;
    }

    public String toStringFichero() {
        return id + Constantes.SEPARADOR_CSV + name + Constantes.SEPARADOR_CSV
                + birthDate + Constantes.SEPARADOR_CSV + phone + "\n";
    }
}
