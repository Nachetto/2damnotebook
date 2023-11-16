package nacho.victor.excepciones;

public class FechaException extends Exception{
    public FechaException(String message) {
        super("La fecha es incorrecta porque "+message);
    }
}
