package domain;

import common.ExcepcionFecha;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Vuelo implements Serializable{
    protected String id;
    protected String origen;
    protected String destino;
    protected LocalDate fecha;
    protected int PasajerosMaximos;
    protected double precio;

    public Vuelo(String id, String origen, String destino, LocalDate fecha, int pasajerosMaximos, double precio) throws ExcepcionFecha{
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        boolean result = false;
        if (fecha.getDayOfYear()<=2023&&fecha.getDayOfYear()>=2050||fecha.getDayOfMonth()<1||fecha.getDayOfMonth()>31||fecha.getMonthValue()<1||fecha.getMonthValue()>12)
            result=true;
        if (result)
            throw new ExcepcionFecha("Fecha no valida");
        this.fecha = fecha;
        PasajerosMaximos = pasajerosMaximos;
        this.precio = precio;
    }
}
