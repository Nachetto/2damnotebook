package com.example.reymortiz_ejercicio1.data.modelo;

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
@Table(name = "equipos")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "equipo", cascade = CascadeType.REMOVE)
    private List<Jugador> jugadores;
}
