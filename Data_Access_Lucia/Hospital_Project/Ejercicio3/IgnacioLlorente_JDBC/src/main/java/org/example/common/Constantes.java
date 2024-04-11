package org.example.common;

public class Constantes {

    public static final String MENU =
            """
                             ********** PART 1 **********
            \n1.- Show all patients
            2.- Show medical records by patient
            3.- Append a new medical record with two medications: Make sure that the patient and the doctor exist
            4.- Delete a patient: If it has any medications associated with one of their medical records, ask the user,
                and if so, delete everything related to the patient before deleting the patient.\n
                             ********** PART 2 **********
            5.- Save to XML
            6.- Get information about the medications of a given patient
            7.- Get the patients that are medicated with Amoxicilina
            8.- Append a new Medical Record to a given patient
            9.- Delete a patient (XML)
            10.- Exit
            """;

    public static final String QUIERE_VER_DEL_1_AL_14_10_PARA_SALIR = "Write the number of the exercise you wanna see from 1 to 9. 10 to exit: ";


    public static final String UNEXPECTED_ERROR_SHOWINH_MENU_AGAIN = "Unexpected error, showing menu again.";
    public static final String GOODBYE = "See Ya!";
    public static final String DESEA_SALIR_PULSE_3 = "Indique si quiere agregar\n un parque de atracciones (escriba 1)\n o un zoologico(escriba 2)\n si desea salir pulse 3";
    public static final String PATIENTDBERROR = "Error in the patients database: ";
    public static final String PATIENTDOESNTEXIST = "The patient does not exist";
    public static final String ERRDB = "Error in the database";
    public static final String DATABASEERR = "Error in the database: ";


    private Constantes() {
    }
}
