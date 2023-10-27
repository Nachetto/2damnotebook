package domain;

import common.ExcepcionFecha;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Internacional extends Vuelo implements Serializable{
    @Getter @Setter
    List<String> escalas;
    public Internacional(String id, String origen, String destino, LocalDate fecha, int pasajerosMaximos, double precio, List<String> escalas) throws ExcepcionFecha {
        super(id, origen, destino, fecha, pasajerosMaximos, precio);
        switch (fecha.getDayOfMonth()) {
            case 7, 8 -> this.precio = precio * 1.25;
        }
        this.escalas=escalas;
    }

    @Override
    public String toString() {
        return "Tipo: Internacional \n" +
                "Id: " + id +"\n" +
                "Origen: " + origen +"\n"+
                "Destino: " + destino +  "\n"+
                "Fecha: " + fecha + "\n" +
                "Escalas: " + escalas + "\n" +
                "Precio: " + precio+"\n\n";
    }
}
