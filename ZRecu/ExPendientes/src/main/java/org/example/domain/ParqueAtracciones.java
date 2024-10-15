package org.example.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.common.PrecioException;
import org.example.common.ValoracionException;

import java.io.Serializable;
import java.time.LocalDate;
public class ParqueAtracciones extends CentroOcio {
    @Getter
    @Setter
    private int edadMinima;

    public ParqueAtracciones(int id, String nombre, int precioEntrada, String provincia, LocalDate fechaConstruccion, String correo, int valoracion, int edadMinima) throws ValoracionException, PrecioException {
        super(id, nombre, precioEntrada, provincia, fechaConstruccion, correo, valoracion);
        if (precioEntrada < 15 || precioEntrada > 25)
            throw new PrecioException(getClass().toString(), "debe de estar entre 15 y 25€ pero su precio actual son " + precioEntrada + "€.");
        this.edadMinima = edadMinima;
    }

    @Override
    public double precioEntrada(int edad, boolean isfestivo) {
        double precioFinal = getPrecioEntrada();
        if (isfestivo)
            precioFinal = precioFinal * 1.4;
        if (edad > 65)
            precioFinal = 0;
        return precioFinal;
    }

    @Override
    public String toString() {
        return super.toString() + edadMinima;
    }
}
