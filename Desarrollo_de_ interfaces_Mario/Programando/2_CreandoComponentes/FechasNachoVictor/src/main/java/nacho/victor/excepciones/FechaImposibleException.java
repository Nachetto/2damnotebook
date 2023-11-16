package nacho.victor.excepciones;

public class FechaImposibleException extends FechaException {

    public FechaImposibleException(String message) {
        super("la fecha es imposible. "+message);
    }
}
