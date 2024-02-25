package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.common.PrecioException;
import org.example.common.ValoracionException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
public class Zoologico extends CentroOcio {
    @Getter  @Setter
    private  List<String> servicios;


    public Zoologico(int id, String nombre, int precioEntrada, String provincia, LocalDate fechaConstruccion, String correo, int valoracion, List<String> servicios) throws ValoracionException, PrecioException {
        super(id, nombre, precioEntrada, provincia, fechaConstruccion, correo, valoracion);
        this.servicios=servicios;
        //no voy a calcular el precio de la entrada aqui segun la edad porque me podría tirar una excepcion asi que digamos que este es el precio base
        if (precioEntrada < 10 || precioEntrada >20)
            throw new PrecioException("Zoológico ", "debe de estar entre 10 y 20€ pero su precio actual son "+precioEntrada+"€.");

    }

    @Override
    public double precioEntrada(int edad, boolean isfestivo) {
        double precioFinal=getPrecioEntrada();
        if (isfestivo)
            precioFinal= precioFinal*1.3;
        if (edad<7)
            precioFinal= 0;

        return precioFinal;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String servicio : servicios) {
            stringBuilder.append(servicio).append(",");
        }

        return "Zoologico;"
                +super.toString()
                +stringBuilder;
    }
}
