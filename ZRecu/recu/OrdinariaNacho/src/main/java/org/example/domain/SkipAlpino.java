package org.example.domain;

import org.example.common.DificultadExcepcion;
import org.example.common.VerificarDificultadExcepcion;
import org.example.service.PistaService;

import java.util.ArrayList;

public class SkipAlpino extends Pista{
    private String dificultad;

    public SkipAlpino(int id, int km, String provincia, String nombre, String dificultad) throws DificultadExcepcion {
        super(id, km, provincia, nombre);
        VerificarDificultadExcepcion.verificandoDificultadExcepcion(dificultad);
        this.dificultad = dificultad;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) throws DificultadExcepcion {
        VerificarDificultadExcepcion.verificandoDificultadExcepcion(dificultad);
        this.dificultad = dificultad;
    }

    @Override
    public String toString() {
        return "SkiAlpino;"+ id+";"+km +";"+provincia +";"+nombre +";"+dificultad+";";
    }
}
