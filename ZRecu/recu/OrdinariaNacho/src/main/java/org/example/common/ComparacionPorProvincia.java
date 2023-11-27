package org.example.common;

import org.example.domain.Pista;

import java.util.Comparator;

public class ComparacionPorProvincia implements Comparator<Pista> {
    @Override
    public int compare(Pista arg0, Pista arg1) {
        int aux=arg0.getProvincia().compareTo(arg1.getProvincia());
        if(aux==0){
            aux=Double.compare(arg0.getKm(),arg1.getKm());
        }
        return aux;
    }
}
