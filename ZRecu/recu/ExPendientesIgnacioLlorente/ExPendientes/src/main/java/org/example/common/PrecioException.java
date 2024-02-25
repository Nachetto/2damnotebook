package org.example.common;

public class PrecioException extends Exception{
    public PrecioException(String tipoEntrada, String mensaje) {
        super("El precio de la entrada para el "+tipoEntrada+"es erroneo porque: ");
    }
}
