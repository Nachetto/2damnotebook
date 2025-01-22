package org.example.nachoHibernateConSpring.domain.model;

import org.example.nachoHibernateConSpring.dao.model.Credential;
import org.example.nachoHibernateConSpring.dao.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientUI {
    private int id;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private int paid;
    private String userName;
    private String password;

    public Patient toPatient() {
        return new Patient(id, name, birthDate, phone, new Credential(userName, password, id));
    }

    public Credential toCredential(int idGenerated) {
        return new Credential(userName, password, idGenerated);
    }
}

