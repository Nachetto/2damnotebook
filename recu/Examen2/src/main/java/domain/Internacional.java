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
    public Internacional(int id, String origen, String destino, LocalDate fecha, int pasajerosMaximos, double precio, List<String> escalas) throws ExcepcionFecha {
        super(id, origen, destino, fecha, pasajerosMaximos, precio);

        boolean result = false;
        if (fecha.getDayOfYear()<=2023&&fecha.getDayOfYear()>=2050||fecha.getDayOfMonth()<1||fecha.getDayOfMonth()>31||fecha.getMonthValue()<1||fecha.getMonthValue()>12)
            result=true;
        if (result)
            throw new ExcepcionFecha("Fecha no valida");
        switch (fecha.getDayOfMonth()) {
            case 7, 8 -> this.precio = precio * 1.25;
        }
        escalas.forEach(escala -> this.precio+=precio*0.2);
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
