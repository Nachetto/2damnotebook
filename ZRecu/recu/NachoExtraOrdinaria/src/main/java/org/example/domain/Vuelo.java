package org.example.domain;
import lombok.Data;
import org.example.common.FechaException;
import org.example.common.NachoVerificacion;

import java.io.Serializable;
import java.time.LocalDate;

@Data
//no pongo @AllArgsConstructor porque la verificación de la fecha está dentro del constructor del vuelo para que no tenga que ponerlo en varios sitios.
public class Vuelo implements Serializable {
    int id,maxpasajeros;double precioavg;
    String aereolinea,origen,destino;
    LocalDate fecha;

    public Vuelo(int id, int maxpasajeros, double precioavg, String aereolinea, String origen, String destino, LocalDate fecha) throws FechaException {
        this.id = id;
        this.maxpasajeros = maxpasajeros;
        this.precioavg = precioavg;
        this.aereolinea = aereolinea;
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        NachoVerificacion.verificar(fecha.getDayOfMonth(),fecha.getMonth(),fecha.getYear());
    }

    @Override
    public String toString() {
        return id +";" + maxpasajeros +";" + precioavg +";" + aereolinea +";" + origen +";" + destino+
                ";" + fecha +";";
    }
}