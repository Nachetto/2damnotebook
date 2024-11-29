package com.hospitalcrud.dao.utilities;

public class SQLQueries {
    public static final String GET_ALL_PATIENTS = "select * from patients";
    public static final String INSERT_PATIENT = "insert into patients (name,date_of_birth,phone) values (?,?,?)";
    public static final String UPDATE_PATIENT = "update patients set name = ?, date_of_birth = ?, phone = ? where patient_id = ?";
    public static final String DELETE_PATIENT = "delete from patients where patient_id = ?";
    public static final String INSERT_CREDENTIAL = "insert into user_login (username,password,patient_id,doctor_id) values (?,?,?,?)";
    public static final String GET_CREDENTIAL = "select * from user_login where username = ?";
    public static final String DELETE_CREDENTIAL = "delete from user_login where patient_id = ?";
    public static final String GET_GROUPED_PAYMENTS = "select sum(amount),patient_id from patient_payments group by patient_id";
    public static final String GET_ALL_PAYMENTS = "select * from patient_payments";
    public static final String GET_MEDICAL_RECORDS = "select * from medical_records where patient_id = ?";
    public static final String GET_PRESCRIBED_MEDICATIONS = "select * from prescribed_medications where record_id = ?";
    public static final String DELETE_MEDICAL_RECORD = "delete from medical_records where record_id = ?";
    public static final String DELETE_PATIENT_PRESCRIBED_MEDICATIONS = "delete pm from prescribed_medications pm join medical_records mr on pm.record_id = mr.record_id where mr.patient_id = ?";
    public static final String INSERT_MEDICAL_RECORD = "insert into medical_records (patient_id,doctor_id,diagnosis,admission_Date) values (?,?,?,?)";
    public static final String INSERT_MEDICATION = "insert into prescribed_medications (record_id,medication_name,dosage) values (?,?,?)";
    public static final String UPDATE_MEDICAL_RECORD = "update medical_records set doctor_id = ?, diagnosis = ?, admission_Date = ? where record_id = ?";
    public static final String GET_ALL_MEDICATIONS = "select t1.* from prescribed_medications t1 join" +
            "(select medication_name, min(prescription_id) as first_id from prescribed_medications group by medication_name)" +
            " t2 on t1.medication_name = t2.medication_name and t1.prescription_id = t2.first_id";
    public static final String GET_ALL_DOCTORS_NAMES = "select name from doctors";
    public static final String GET_ALL_DOCTORS = "select * from doctors";
    public static final String DELETE_PATIENT_MEDICAL_RECORDS = "delete from medical_records where patient_id = ?";
    public static final String DELETE_PATIENT_PAYMENTS = "delete from patient_payments where patient_id = ?";
    public static final String DELETE_PRESCRIBED_MEDICATIONS = "delete from prescribed_medications where record_id = ?";
    public static final String DELETE_PATIENT_APPOINTMENTS = "delete form appointments where patient_id = ?";

    private SQLQueries() {
    }
}
