package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "services")
@NamedQuery(name="selectServiceByName",query = "SELECT s FROM ServicesEntity s where s.name = :serviceName")
public class ServicesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "price")
    private int price;
}
