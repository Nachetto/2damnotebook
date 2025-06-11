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


    public Customer(String fileLine) {
        String[] elemArray = fileLine.split(";");
        this.id = Integer.parseInt(elemArray[0]);
        this.name = elemArray[1];
        this.surname = elemArray[2];
        this.email = elemArray[3];
        this.phone = !elemArray[4].isEmpty() ? Integer.parseInt(elemArray[4]) : 0;
        this.birthdate = LocalDate.parse(elemArray[5]);
        this.credential = new Credential(name, "1234");
    }



    public String toStringTextFile() {
        return id + ";" + name + ";" + surname + ";" + email + ";" + phone + ";" + birthdate;
    }

}
