package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "phone")
    private int phone;

    @Column(name = "first_name")
    private String name;

    @Column(name = "last_name")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate birthdate;

    private Credential credential;

    public Customer(String fileLine) {
        String[] elemArray = fileLine.split(";");
        this.id = Integer.parseInt(elemArray[0]);
        this.name = elemArray[1];
        this.surname = elemArray[2];
        this.email = elemArray[3];
        this.phone = !elemArray[4].isEmpty() ? Integer.parseInt(elemArray[4]) : 0;
        this.birthdate = LocalDate.parse(elemArray[5]);
        this.credential = new Credential("root", "2dam");
    }


    public String toStringTextFile() {
        return id + ";" + name + ";" + surname + ";" + email + ";" + phone + ";" + birthdate;
    }

}
