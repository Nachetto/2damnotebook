package com.example.reyortiz_ejercicio3.data.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "password")
    private String password;
    @Column(name = "fechaNac")
    private LocalDate fechaNac;
    @Column(name = "rol")
    private String rol;
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.REMOVE)
    private List<Movil> moviles;
}
