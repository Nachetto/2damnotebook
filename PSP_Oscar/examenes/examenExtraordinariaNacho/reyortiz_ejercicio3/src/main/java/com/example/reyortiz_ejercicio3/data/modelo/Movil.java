package com.example.reyortiz_ejercicio3.data.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "moviles")
public class Movil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "year")
    private int year;
    @Column(name = "capacidad")
    private int capacidad;
    @Column(name = "empleado")
    private int empleado;
}
