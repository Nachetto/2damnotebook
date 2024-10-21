package com.hospitalcrud.common;

public class Constants {
    public static final String CONFIG_FILE_PATH_XML = "config/config.xml";
    //TODO TODAS LAS CONSTANTES DE LA APLICACION

    private Constants() {
    }

    //PROPERTIES FILE PATH
    public static final String CONFIG_FILE_PATH = "config/config.properties";

    //DATA FILES PATH PROPERTY NAMES
    public static final String PATH_MEDICAL_RECORDS = "pathMedicalRecords";
    public static final String PATH_PATIENTS = "pathPatients";
    public static final String PATH_DOCTORS = "pathDoctors";

    public static final String NEXT_ID_PATIENT = "config/nextIdPatient";
    public static final String NEXT_ID_DOCTOR = "config/nextIdDoctor";
}
