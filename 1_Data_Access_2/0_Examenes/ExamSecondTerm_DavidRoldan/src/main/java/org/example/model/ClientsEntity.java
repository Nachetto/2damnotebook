package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "clients")
@NamedQuery(name="selectClientByName",query = "SELECT c FROM ClientsEntity c where c.name = :clientName"
)
public class ClientsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "membership_type", nullable = false, length = 100)
    private String membershipType;

    @Column(name = "balance", nullable = false, precision = 0)
    private double balance;
}
