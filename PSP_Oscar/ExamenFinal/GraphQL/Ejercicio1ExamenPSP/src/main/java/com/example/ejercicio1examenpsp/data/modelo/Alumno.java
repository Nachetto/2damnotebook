package com.example.ejercicio1examenpsp.data.modelo;

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
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany
    @JoinColumn(name = "alumno")
    private List<Asignatura> asignaturas;


}
