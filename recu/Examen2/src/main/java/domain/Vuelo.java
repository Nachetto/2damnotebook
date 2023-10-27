package domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Vuelo implements Serializable{
    protected int id;
    protected String origen;
    protected String destino;
    protected LocalDate fecha;
    protected int PasajerosMaximos;
    protected double precio;

    public Vuelo(int id, String origen, String destino, LocalDate fecha, int pasajerosMaximos, double precio) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.fecha=fecha;
        this.fecha = fecha;
        PasajerosMaximos = pasajerosMaximos;
        this.precio = precio;
    }
}
