package common;


import java.io.IOException;

public class ExcepcionDificultad extends Exception{
    public ExcepcionDificultad(String dificultad) throws IOException {
        try{
            boolean result=false;
            if (dificultad.equalsIgnoreCase("verde")||dificultad.equalsIgnoreCase("azul")||dificultad.equalsIgnoreCase("roja"))
                result=true;
            if (!result)
                throw new IOException(Constantes.EXCEPCION);
        }
        catch (NullPointerException e){
            throw new IOException(Constantes.EXCEPCION+" "+e.getMessage());
        }
    }
}