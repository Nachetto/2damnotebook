package org.example.dao.common;

public class SQLConstants {
    /*para calcular total ammount payed, necesitas guardar una lista de
    medication ids que formen parte de los records asociados al patient esecifico,
    con esa lista te vas a la tabla de Payment y sumas todos los ammount where medicationID == i*/
    public static final String TOTAL_AMMOUNT_PAID_BY_PATIENT =
        "SELECT SUM(p.Amount) AS TotalAmountPaid " +
                "FROM Payment p " +
                "INNER JOIN PrescribedMedication pm ON p.MedicationID = pm.MedicationID "+
                "INNER JOIN Record r ON pm.RecordID = r.RecordID "+
                "WHERE r.PatientID = ?;";
    public static final String CREDENTIALSFROMPATIENTORDOCTORID_QUERY =
            "SELECT * FROM User_Authentication WHERE PatientOrDoctorID = ?;";
    public static final String GETALLPATIENTS_QUERY =
            "SELECT * FROM Patient;";
    public static final String MEDICATIONSBYPATIENTID_QUERY =
            "SELECT * FROM PrescribedMedication WHERE RecordID IN (SELECT RecordID FROM Record WHERE PatientID = ?);";
    public static final String GETALLRECORDS_QUERY = "SELECT * FROM Record;";
    public static final String ISTYPEPATIENTFROMUSERNAME_QUERY = "SELECT isTypePatient FROM User_Authentication WHERE username = ?;";
    public static final String AUTHENTICATION_QUERY =
            "SELECT IF(COUNT(*) > 0, 1, 0) AS isAuthenticated " +
                    "FROM IgnacioLlorente_HospitalDataAccess.User_Authentication " +
                    "WHERE Username = ? AND Password = ?;";
    public static final String RECORDSFROMPATIENTID_QUERY = "SELECT * FROM Record WHERE PatientID = ?;";
    public static final String MEDICATIONSFROMRECORDID_QUERY = "SELECT * FROM PrescribedMedication WHERE RecordID = ?;";
    public static final String RECORD_INSERT = "INSERT INTO Record (PatientID, Diagnosis, DoctorID) VALUES (?, ?, ?);";
    public static final String MEDICATION_INSERT = "INSERT INTO PrescribedMedication (Name, Dosage, RecordID) VALUES (?, ?, ?);";
    public static final String COUNTMEDICATIONSFROMRECORDID_QUERY = "SELECT COUNT(*) AS count FROM PrescribedMedication WHERE RecordID = ?;";
    public static final String MEDICATION_DELETEBYPATIENTID = "DELETE FROM PrescribedMedication WHERE RecordID IN (SELECT RecordID FROM Record WHERE PatientID = ?);";
    public static final String PATIENT_DELETE = "DELETE FROM Patient WHERE PatientID = ?;";
}