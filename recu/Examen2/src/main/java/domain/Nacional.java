package domain;

import common.ExcepcionFecha;

import java.io.Serializable;
import java.time.LocalDate;

public class Nacional extends Vuelo implements Serializable{
    public Nacional(String id, String origen, String destino, LocalDate fecha, int pasajerosMaximos, double precio) throws ExcepcionFecha {
        super(id, origen, destino, fecha, pasajerosMaximos, precio);
        switch (fecha.getDayOfMonth()) {
            case 7 -> this.precio = precio * 1.20;
            case 8 -> this.precio = precio * 1.30;
        }
    }

    @Override
    public String toString() {
        return "Tipo: Nacional \n" +
                "Id: " + id +"\n" +
                "Origen: " + origen +"\n"+
                "Destino: " + destino +  "\n"+
                "Pasajeros Maximos: " + PasajerosMaximos + "\n" +
                "Precio: " + precio+"\n\n";
    }
}
