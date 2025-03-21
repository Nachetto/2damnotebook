package com.hospitalcrud.dao.model;

import com.hospitalcrud.domain.model.PatientUI;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
@NamedQueries({
    @NamedQuery(name = "Patient.getAll", query = "FROM Patient ")
})
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private int id;
    @Column
    private String name;
    @Column(name = "date_of_birth")
    private LocalDate birthDate;
    @Column
    private String phone;

    @Transient //ignore
    private Credential credential;

    public Patient(int id, String name, LocalDate dob, String phone) {
        this.id = id;
        this.name = name;
        this.birthDate = dob;
        this.phone = phone;
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
