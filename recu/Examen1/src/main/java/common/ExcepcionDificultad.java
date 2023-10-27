package common;

public class ExcepcionDificultad extends Exception {
    public ExcepcionDificultad(String dificultad) {
        super(Constantes.EXCEPCION+"\nHas puesto: "+dificultad);
    }
}

