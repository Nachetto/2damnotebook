package org.example.nachoHibernateConSpring.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "doctors")
@NamedQueries({
        @NamedQuery(name = "Doctor.getAll", query = "FROM Doctor")
})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "specialization")
    private String specialty;
    @Column(name = "phone")
    private String phone;


    public Doctor(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }
}