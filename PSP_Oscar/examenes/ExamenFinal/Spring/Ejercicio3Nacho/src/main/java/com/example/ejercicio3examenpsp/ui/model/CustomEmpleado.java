package com.example.ejercicio3examenpsp.ui.model;

import com.example.ejercicio3examenpsp.data.modelo.Movil;
import lombok.Getter;

import java.util.List;

@Getter
public class CustomEmpleado {
    private String nombre;
    private List<Movil> moviles;

    public CustomEmpleado(String nombre, List<Movil> moviles) {
        this.nombre = nombre;
        this.moviles = moviles;
    }
}