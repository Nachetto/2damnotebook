package com.hospitalcrud.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_login")
@NamedQueries({
        @NamedQuery(name = "Credential.login", query = "FROM Credential where username = :username and password = :password"),
        @NamedQuery(name = "Credential.validate_username", query = " SELECT COUNT(c) FROM Credential c WHERE c.username = :username"),
        @NamedQuery(name = "Credential.getAll", query = "FROM Credential ")
})
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "doctor_id")
    private Integer doctorId;
    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    public Credential(String username, String password, int patientId) {
        this.username = username;
        this.password = password;
        this.patientId = patientId;
    }
}
