package com.example.ejercicio3examenpsp.data.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "ratones")
public class Raton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autogenerado
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "edad")
    private int edad;

}
