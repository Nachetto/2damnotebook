package com.hospitalcrud.common;

public class Constants {
    public static final String CONFIG_FILE_PATH_XML = "config/config.xml";

    //patient queries
    public static final String GET_ALL_PATIENTS = "SELECT * FROM patients";
    public static final String INSERT_PATIENT = "INSERT INTO patients (name, date_of_birth, phone) VALUES (?, ?, ?)";
    public static final String UPDATE_PATIENT = "UPDATE patients SET name = ?, date_of_birth = ?, phone = ? WHERE patient_id = ?";
    public static final String DELETE_PATIENT = "DELETE FROM patients WHERE patient_id = ?";

    //doctor queries
    public static final String GET_ALL_DOCTORS = "SELECT * FROM doctors";

    //medical record queries
    public static final String GET_ALL_MED_RECORDS = "SELECT * FROM medical_records";
    public static final String GET_MED_RECORDS_BY_PATIENT_ID = "SELECT * FROM medical_records WHERE patient_id = ?";
    public static final String INSERT_MED_RECORD = "INSERT INTO medical_records (patient_id, doctor_id, diagnosis, admission_date) VALUES (?, ?, ?, ?); ";
    public static final String UPDATE_MED_RECORD = "UPDATE medical_records SET patient_id = ?, doctor_id = ?, diagnosis = ?, admission_date = ? WHERE record_id = ?";
    public static final String DELETE_MED_RECORD = "DELETE FROM medical_records WHERE record_id = ?";
    public static final String DELETE_APPOINTMENTS = "DELETE FROM appointments WHERE patient_id = ?";

    //Credential queries
    public static final String CHECK_USERNAME = "SELECT * FROM user_login WHERE username = ?";
    public static final String LOGIN = "SELECT * FROM user_login WHERE username = ? AND password = ?";
    public static final String ADD_CREDENTIAL = "INSERT INTO user_login (username, password, patient_id, doctor_id) VALUES (?, ?, ?, NULL)";
    public static final String DELETE_CREDENTIAL = "DELETE FROM user_login WHERE patient_id = ?";
    public static final String ADD_DOCTORS = "INSERT INTO doctors (name, specialization, phone) VALUES (?, ?, ?)";
    public static final String DELETE_DOCTORS = "DELETE FROM doctors WHERE doctor_id = ?";
    public static final String GETDOCTORBYID = "SELECT * FROM doctors WHERE doctor_id = ?";

    //Medication queries
    public static final String GET_BY_RECORDID = "SELECT * FROM prescribed_medications WHERE record_id = ? LIMIT 1000";
    public static final String SAVE_MEDICATION = "INSERT INTO prescribed_medications (record_id, medication_name, dosage) VALUES (?,?,null)";
    public static final String UPDATE_MEDICATION = "UPDATE prescribed_medications SET medication_name = ? WHERE prescription_id = ?";
    public static final String DELETE_MEDICATION = "DELETE FROM prescribed_medications WHERE record_id = ?";
    public static final String RETRIEVE_PATIENT_ID =  "SELECT patient_id FROM patients WHERE name = ? AND date_of_birth = ? AND phone = ?";


    public static final String DELETE_PAYMENT = "DELETE FROM patient_payments WHERE patient_id = ?";
    public static final String DELETE_PATIENT_MED_RECORDS = "DELETE FROM medical_records WHERE patient_id = ?";

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
