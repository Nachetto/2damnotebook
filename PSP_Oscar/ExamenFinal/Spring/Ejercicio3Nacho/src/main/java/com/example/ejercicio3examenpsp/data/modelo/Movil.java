package com.example.ejercicio3examenpsp.data.modelo;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autogenerado
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "anyo")
    private int anyo;
    @Column(name = "capacidad")
    private int capacidad;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
}
