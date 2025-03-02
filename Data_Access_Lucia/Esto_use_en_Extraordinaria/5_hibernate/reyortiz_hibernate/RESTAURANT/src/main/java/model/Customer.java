package model;

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
@Table(name = UtilitiesModel.CUSTOMER, schema = UtilitiesModel.REYMORTIZ_RESTAURANT)
public class Customer {
    @Id
    @Column(name = UtilitiesModel.IDCUSTOMER, nullable = false)
    private Integer idcustomer;

    @Column(name = UtilitiesModel.FIRST_NAME, nullable = false, length = 45)
    private String firstName;

    @Column(name = UtilitiesModel.LAST_NAME, nullable = false, length = 45)
    private String lastName;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = true, length = 45)
    private String phone;

    @Column(name = UtilitiesModel.DATE_OF_BIRTH, nullable = true)
    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = UtilitiesModel.IDCUSTOMER, referencedColumnName = UtilitiesModel.IDCREDENTIALS, nullable = false)
    private Credentials credentials;


    public Customer(String line) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(UtilitiesModel.YYYY_MM_DD);
        String[] part = line.split(",");

        this.idcustomer = Integer.parseInt(part[0]);
        this.firstName = part[1];
        this.lastName = part[2];
        this.email = part[3];
        this.phone = part[4];
        this.dateOfBirth = LocalDate.parse(part[5], formatter);
    }

    public String stringToTextFile() {
        return idcustomer + "," + firstName + "," + lastName + "," + email + "," + phone + "," + dateOfBirth;
    }

    public String toStringName() {
        return firstName + ' ' + lastName;
    }
}
