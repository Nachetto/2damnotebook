package domain;

import common.ExcepcionDificultad;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;

public class SkiAlpino extends Pista implements Serializable {
    @Getter
    @Setter
    private String dificultad;

    public SkiAlpino(String nombre, String provincia, int id, int km, String dificultad) throws ExcepcionDificultad {
        super(nombre, provincia, id, km);
        boolean result = false;
        if (dificultad.equalsIgnoreCase("verde") || dificultad.equalsIgnoreCase("azul") || dificultad.equalsIgnoreCase("roja"))
            result = true;
        if (!result)
            throw new ExcepcionDificultad(dificultad);
        this.dificultad = dificultad;
    }

    @Override
    public String toString() {
        return "Tipo: SkiAlpino\n" +
                "Id: " + id + "\n" +
                "Provincia: " + provincia + "\n" +
                "Nombre: " + nombre + "\n" +
                "Dificultad: " + dificultad + "\n" +
                "Km: " + km + "\n\n";
    }
}
