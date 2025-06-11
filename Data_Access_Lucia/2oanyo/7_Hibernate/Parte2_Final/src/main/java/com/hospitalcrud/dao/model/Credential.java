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
        @NamedQuery(name = "Credential.login", query = "select Credential FROM Credential where username = :username and password = :password"),
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
    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private Patient patient;
    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;


    public Credential(String userName, String password, int id){
        this.id = id;
        this.username = userName;
        this.password = password;
    }
}
