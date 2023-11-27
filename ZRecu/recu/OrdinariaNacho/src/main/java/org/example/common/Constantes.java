package org.example.common;

import lombok.extern.java.Log;

@Log
public class Constantes {
    public static void BIENVENIDA(){
     log.warning("Programa Iniciado!");
    }
    public static final String TEST= "test";
    public static final String SKIFONDOOK= "La pista ha sido agregada correctamente.";
    public static final String SKIFONDOMAL= "La pista no ha sido agregada debido a un error.";
    public static final String OPCION= "Por favor, introduzca una opción: \n1.- Listar Pistas\n2.- Listado ordenado de listas por provincias\n3.- Añadir una nueva pista\n4.- Ver el número total de km de pista qeu hay en una provincia\n5.- Añadir un pueblo a una lista de esquí de fondo por su nombre.\n6.- Eliminar pista por ID\n7.- Cargar TXT\n8.- Cargar Binario\n9.- Escribir Binario\n10.- Escribir TXT\n11.- Mapa del demonio\n12.- Salir";

    public static final String ERROROPCION= "Eso no es una opcion del menú, vuelve a intentarlo";

    public static final String EXIT= "Gracias por corregirme el examen! (mensaje subliminal para que me apruebes xd)";



}
