package org.example.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.common.FechaException;

import java.io.Serializable;
import java.time.LocalDate;

public class Nacional extends Vuelo implements Serializable {
    public Nacional(int id, int maxpasajeros, double precioavg, String aereolinea, String origen, String destino, LocalDate fecha) throws FechaException {
        super(id, maxpasajeros, precioavg, aereolinea, origen, destino, fecha);
        if (fecha.getMonth().equals(7)){
            this.precioavg=this.getPrecioavg()+(this.precioavg*0.2);
        }
        else if (fecha.getMonth().equals(8)) {
            this.precioavg=this.getPrecioavg()+(this.precioavg*0.3);
        }

    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Nacional;"+super.toString();
    }
}
