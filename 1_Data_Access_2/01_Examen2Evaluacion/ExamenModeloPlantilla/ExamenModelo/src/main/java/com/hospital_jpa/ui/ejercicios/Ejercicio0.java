package com.hospital_jpa.ui.ejercicios;


import com.hospital_jpa.dao.cargaDatos.CargaDatos;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;


public class Ejercicio0 {
    public static void main(String[] args) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        CargaDatos cargaDatos = container.select(CargaDatos.class).get();

        cargaDatos.cargarDatos();

    }
}
