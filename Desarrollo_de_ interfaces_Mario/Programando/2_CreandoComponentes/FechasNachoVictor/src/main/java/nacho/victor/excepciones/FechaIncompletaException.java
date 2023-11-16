package nacho.victor.excepciones;

public class FechaIncompletaException extends FechaException {
    public FechaIncompletaException() {
        super("la fecha está incompleta. ");
    }
}