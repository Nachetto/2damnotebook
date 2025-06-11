package org.example.common;

public class Constantes {

    public static final String MENU_ADMIN =
            """
                             
                             ********** PART 1 **********
            1.- Show all patients
            2.- Show medical records by patient
            3.- Append a new medical record with two medications: Make sure that the patient and the doctor exist
            4.- Delete a patient: If it has any medications associated with one of their medical records, ask the user,
                and if so, delete everything related to the patient before deleting the patient.
                
                             ********** PART 2 **********
            5.- Save to XML
            6.- Get information about the medications of a given patient
            7.- Get the patients that are medicated with Amoxicilina
            8.- Append a new Medical Record to a given patient
            9.- Delete a patient (XML)
            
                             ********** PART 3 **********
            10.- Show the information of all patients, including the total amount paid
            11.- Show medical records by patient and, when clicking on one, show all the prescribed medication
            12.- Append a new medical record with two medicines: Make sure that the patient and the medication exist
            13.- Delete a patient: If it has any medication, ask the user first, and if so, delete the patient with all
                 their data
            14.- Find the patient with the most medical records
            15.- Find the date with more patients
            16.- Exit
            
            """;

    public static final String MENU_DOCTOR =
            """
            
                             ********** DOCTOR MENU **********
            Add and modify medical records and prescribed medication
            1.- Show all medical records asignated to this doctor
            2.- Add a new medical record asignated to this doctor
            3.- Modify a medical record asignated to this doctor
            4.- Add a new medication assigned to this doctor
            5.- Modify a medication assigned to this doctor
            6.- Exit
            
            """;

    public static final String CHOOSE_ECERCISE_NUMBER_ADMIN = "Write the number of the exercise you wanna see from 1 to 15. 16 to exit: ";
    public static final String CHOOSE_ECERCISE_NUMBER_DOCTOR = "Write the number of the exercise you wanna see from 1 to 5. 6 to exit: ";

    public static final String UNEXPECTED_ERROR_SHOWINH_MENU_AGAIN = "Unexpected error, showing menu again.";
    public static final String GOODBYE = "See Ya!";
    public static final String PATIENTDBERROR = "Error in the patients database: ";
    public static final String PATIENTDOESNTEXIST = "The patient does not exist";
    public static final String ERRDB = "Error in the database";
    public static final String DATABASEERR = "Error in the database: ";
    public static final String MEDICATIONDBERROR = "Error in the medications database: ";


    private Constantes() {
    }
}
