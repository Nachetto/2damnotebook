package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Customer {
    private int id, phone;
    private String name, surname, email;
    private LocalDate birthdate;
    private Credential credential;

    public Customer(int id, int phone, String name, String surname, String email, Credential credentials, LocalDate birthdate) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthdate = birthdate;
        credential = credentials;
    }
}
