package com.hospitalcrud.common;

public class Constants {
    public static final String CONFIG_FILE_PATH_XML = "config/config.xml";


    //patient queries
    public static final String GET_ALL_PATIENTS = "SELECT * FROM patients";
    public static final String INSERT_PATIENT = "INSERT INTO patients (name, date_of_birth, phone) VALUES (:name, :birthdate, :phone)";
    public static final String UPDATE_PATIENT = "UPDATE patients SET name = :name, date_of_birth = :birthdate, phone = :phone WHERE patient_id = :id";
    public static final String DELETE_PATIENT = "DELETE FROM patients WHERE patient_id = :id";


    //doctor queries
    public static final String GET_ALL_DOCTORS = "SELECT * FROM doctors";


    //medical record queries
    public static final String GET_ALL_MED_RECORDS = "SELECT * FROM medical_records";
    public static final String GET_MED_RECORDS_BY_PATIENT_ID = "SELECT * FROM medical_records WHERE patient_id = ?";
    public static final String INSERT_MED_RECORD = "INSERT INTO medical_records (patient_id, doctor_id, diagnosis, admission_date) VALUES (:1, :2, :3, :4)";
    public static final String UPDATE_MED_RECORD = "UPDATE medical_records SET patient_id = :1, doctor_id = :2, diagnosis = :3, admission_date = :4 WHERE record_id = :5";
    public static final String DELETE_MED_RECORD = "DELETE FROM medical_records WHERE record_id = :?";


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
    public static final String PATH_DRIVER = "dbDriver";
}
