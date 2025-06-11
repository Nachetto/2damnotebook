package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Customer {
    private int id;
    private Integer phone; // Change from 'int' to 'Integer'
    private String name, surname, email;
    private LocalDate birthdate;
    private Credential credential;

    // Your constructors and other methods should now use Integer for phone
    public Customer(int id, Integer phone, String name, String surname, String email, Credential credentials, LocalDate birthdate) {
        this.id = id;
        this.phone = phone; // This can now be null
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthdate = birthdate;
        this.credential = credentials;
    }

    // ... other constructors and methods
}
