package org.example.common;

public class Constantes {

    public static final String MENU = "\n1.- Show all patients" +
            "\n2.- Show medical records by patient" +
            "\n3.- Append a new medical record with two medications: Make sure that the patient and the doctor exist" +
            "\n4.- Delete a patient: If it has any medications associated with one of their medical records, ask the user, and if so, delete everything related to the patient before deleting the patient." +
            "\n5.- EXIT";

    public static final String QUIERE_VER_DEL_1_AL_14_15_PARA_SALIR = "\n\nWrite the number of the exercise you wanna see from 1 to 4. 5 to exit: ";


    public static final String UNEXPECTED_ERROR_SHOWINH_MENU_AGAIN = "Unexpected error, showing menu again.";
    public static final String GOODBYE = "See Ya!";
    public static final String DESEA_SALIR_PULSE_3 = "Indique si quiere agregar\n un parque de atracciones (escriba 1)\n o un zoologico(escriba 2)\n si desea salir pulse 3";
    public static final String PATIENTDBERROR = "Error in the patients database: ";
    public static final String PATIENTDOESNTEXIST = "The patient does not exist";
    public static final String ERRDB = "Error in the database";


    private Constantes(){    }
}
