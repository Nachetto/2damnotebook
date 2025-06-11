package com.hospital_jpa.domain.model;

import com.hospital_jpa.dao.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientUI {
    private int id;
    private String name;
    private LocalDate birthDate;
    private int paid;
    private String password;
    private String phone;
    private String userName;
     public PatientUI(Patient patient) {
         id = patient.getId();
         name = patient.getName();
         birthDate = patient.getBirthDate();
         paid = 0;
         password = "";
         phone = patient.getPhone();
         userName = "";
     }
}
