package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private int id;
    private int phone;
    private String name;
    private String surname;
    private String email;
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
