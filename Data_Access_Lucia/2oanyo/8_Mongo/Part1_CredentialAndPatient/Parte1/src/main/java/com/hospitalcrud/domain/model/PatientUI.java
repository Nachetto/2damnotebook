package com.hospitalcrud.domain.model;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientUI {
    private int id; // ID mapeado a int
    private String name;
    private LocalDate birthDate;
    private String phone;
    private int paid;
    private String userName;
    private String password;

    public Patient toPatient(ObjectId id) {
        return new Patient(id, name, birthDate, phone, new Credential(ObjectId.get(), userName, password, this.id, -1));
    }
}

