package domain;

import common.ExcepcionDificultad;
import lombok.Getter;
import lombok.Setter;

public class SkiAlpino extends Pista {
    @Getter @Setter
    private String dificultad;
    public SkiAlpino(String nombre, String provinca, int id, int km, String dificultad) throws ExcepcionDificultad {
        super(nombre, provinca, id, km);
        this.dificultad=dificultad;
    }
}
