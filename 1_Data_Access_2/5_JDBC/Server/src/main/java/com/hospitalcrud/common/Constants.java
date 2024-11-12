package com.hospitalcrud.common;

public class Constants {
    public static final String CONFIG_FILE_PATH_XML = "config/config.xml";



    public static final String GET_ALL_PATIENTS = "SELECT * FROM patients";
    public static final String INSERT_PATIENT = "INSERT INTO patients (name, date_of_birth, phone) VALUES (?, ?, ?)";
    public static final String UPDATE_PATIENT = "UPDATE patients SET name = ?, date_of_birth = ?, phone = ? WHERE patient_id = ?";

    private Constants() {

    }

    //DATA FILES PATH PROPERTY NAMES
    public static final String PATH_MEDICAL_RECORDS = "pathMedicalRecords";
    public static final String PATH_PATIENTS = "pathPatients";
    public static final String PATH_DOCTORS = "pathDoctors";
    public static final String PATH_DB_URL = "dbUrl";
    public static final String PATH_DB_USER = "dbUser";
    public static final String PATH_DB_PASSWORD = "dbPassword";

    public static final String NEXT_ID_PATIENT = "nextIdPatient";
    public static final String NEXT_ID_DOCTOR = "nextIdDoctor";
    public static final String NEXT_ID_MED_RECORD = "nextIdMedRecord";
}
