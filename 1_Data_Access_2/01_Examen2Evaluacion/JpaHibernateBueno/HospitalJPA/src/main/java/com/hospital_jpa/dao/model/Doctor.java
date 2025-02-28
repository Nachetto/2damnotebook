package com.hospital_jpa.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "doctors")
@NamedQuery(name = "GET_ALL_DOCTORS", query = "from Doctor")
public class Doctor {
    @Id
    @GeneratedValue
    @Column(name = "doctor_id")
    private int doctorId;
    @Column(name = "name")
    private String name;
    @Column(name = "specialization")
    private String specialization;
    @Column(name = "phone")
    private String phone;
}
