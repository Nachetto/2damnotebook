package org.example.common;

public class Constantes {

    public static final String MENU = "\n1.- Listado de todos los centros de ocio" +
            "\n2.- Listado ordenado de centros por provincia y a igualdad de provincia por fecha de construccion" +
            "\n3.- Añadir un Centro nuevo" +
            "\n4.- Consulta de los centros de ocio que hay por provincia, el metodo o metodos informara de cuantos centros hay" +
            "y el precio base medio de la entrada que tiene una provincia" +
            "\n5.- Añadir un servicio a la lista de servicios del id del zoologico proporcionado" +
            "\n6.- Devolver una coleccion de los parques de atracciones ordenados por provincia y a igualdad de" +
            "provincia por precio de entrada para una edad introducido por el usuario y si es festivo o no introducido por el usuario" +
            "\n7.- Eliminar todos los centros de ocio que se hayan construido antes de un año determinado devolviendo\n" +
            "el numero de centros eliminados" +
            "\n8.- Volcar toda la informacion a un fichero de texto" +
            "\n9.- Volcar toda la informacion a un fichero binario" +
            "\n10.- Pasar todos los parques de atracciones a un fichero de JSON" +
            "\n11.- Mostrar la informacion del fichero JSON que se ha creado en el ejercicio anterior" +
            "\n12.- Leer la informacion de un fichero binario y cargarla en la lista de centros de ocio" +
            "\n13.- Leer la informacion de un fichero de texto y cargarla en la lista de centros de ocio" +
            "\n14.- Método que devuelva un mapa que contenga para cada provincia, la lista de centros de ocio que existen.\n" +
            "Verificar la salida de dicho Map y aprovechar para obtener el número de zoos y el de parques de atracciones de cada provincia." +
            "\n15.- SALIR";

    public static final String QUIERE_VER_DEL_1_AL_14_15_PARA_SALIR = "\n\nEscriba que ejercicio quiere ver, del 1 al 14. 15 para salir: ";


    public static final String ERROR_VUELVO_A_SACAR_EL_MENU = "Error inesperado, vuelvo a sacar el menu.";
    public static final String HASTA_LUEGO = "Hasta luego!";
    public static final String SI_DESEA_SALIR_PULSE_3 = "Indique si quiere agregar\n un parque de atracciones (escriba 1)\n o un zoologico(escriba 2)\n si desea salir pulse 3";
    public static final String DESEA_SALIR_PULSE_3 = "Indique si quiere agregar\n un parque de atracciones (escriba 1)\n o un zoologico(escriba 2)\n si desea salir pulse 3";




    private Constantes(){    }
}
