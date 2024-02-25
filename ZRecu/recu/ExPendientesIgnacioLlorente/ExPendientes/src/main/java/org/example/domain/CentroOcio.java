package org.example.domain;

import org.example.common.ValoracionException;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class CentroOcio implements Serializable { //no la hago serializable porque no voy a hacer los metodos de cargar y leer binario, en los hijos tampoco lo voy a hacer serializable, pero se que hace falta si quieres hacer lo de binario
    private final int id;
    private final String nombre;
    private final int precioEntrada;
    private final String provincia;
    private final LocalDate fechaConstruccion;
    private final String correo;
    private final int valoracion;
    protected abstract double precioEntrada(int edad, boolean isfestivo);

    protected CentroOcio(int id, String nombre, int precioEntrada, String provincia, LocalDate fechaConstruccion, String correo, int valoracion) throws ValoracionException {
        this.id = id;
        this.nombre = nombre;
        this.precioEntrada = precioEntrada;
        this.provincia = provincia;
        this.fechaConstruccion = fechaConstruccion;
        this.correo = correo;
        this.valoracion = valoracion;
        if (valoracion < 1 || valoracion > 5)
            throw new ValoracionException("Error, la valoracion debe estar entre 1 y 5 estrellas");
    }

    public int getPrecioEntrada() {
        return precioEntrada;
    }

    public String getProvincia() {
        return provincia;
    }

    public LocalDate getFechaConstruccion() {
        return fechaConstruccion;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return  id +  ";" +
                nombre + ";" +
                precioEntrada + ";" +
                provincia +  ";" +
                fechaConstruccion + ";" +
                correo +  ";" +
                valoracion;
    }
}
