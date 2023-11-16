package nacho.victor.excepciones;

public class FechaIncorrectaException extends FechaException {
    public FechaIncorrectaException(String message) {
        super("la fecha es no está bien puesta. "+message);
    }
}