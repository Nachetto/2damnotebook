package com.example.ejercicio3examenpsp.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
//da igual que este en minusculas, no es case sensitive el sql
@Table(name = "informes")
public class Informe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autogenerado
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "rol")
    private String rol;

}
