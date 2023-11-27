package org.example.domain;


import org.example.common.FechaException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


public class Internacional extends Vuelo implements Serializable {
    List<String> escala;
    public Internacional(int id, int maxpasajeros, double precioavg, String aereolinea, String origen, String destino, LocalDate fecha) throws FechaException {
        super(id, maxpasajeros, precioavg, aereolinea, origen, destino, fecha);
        escala=new ArrayList<>();
        if (fecha.getMonth().equals(7)||fecha.getMonth().equals(8)){
            this.precioavg=this.getPrecioavg()+(this.precioavg*0.25);
        }
        descontarEscala();
    }

    public List<String> getEscala() {
        return escala;
    }

    public void setEscala(List<String> escala) {
        this.escala = escala;
    }

    public boolean nuevaEscala(String escalas) {
        return getEscala().add(escalas);
    }

    private void descontarEscala() {
        int count=0;
        for (String e: escala) {
            count++;
        }
        double descuentoescala=20*count;
        this.precioavg=this.getPrecioavg()+(this.precioavg*(descuentoescala/100));
    }

    @Override
    public String toString() {
        return "Internacional"+super.toString()+escala+";";
    }
}
