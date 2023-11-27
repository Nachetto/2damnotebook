package org.example.common;

public class VerificarDificultadExcepcion{
    public static void verificandoDificultadExcepcion(String dificultad) throws DificultadExcepcion{
        boolean respuesta=true;
        if (dificultad.equalsIgnoreCase("verde") || dificultad.equalsIgnoreCase("azul")
                || dificultad.equalsIgnoreCase("roja"))
            respuesta=false;
        if (respuesta)
            throw new DificultadExcepcion("La dificultad introducida es incorrecta");
    }
}
