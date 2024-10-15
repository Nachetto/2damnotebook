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
    
    public static final String GETALLPATIENTSWITHTOTALAMMOUNTPAID_QUERY =
            "SELECT pt.*, SUM(p.Amount) AS TotalAmountPaid " +
                    "FROM Payment p " +
                    "INNER JOIN PrescribedMedication pm ON p.MedicationID = pm.MedicationID " +
                    "INNER JOIN Record r ON pm.RecordID = r.RecordID " +
                    "INNER JOIN Patient pt ON r.PatientID = pt.PatientID " +
                    "GROUP BY pt.PatientID;";
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
    public static final String PATIENTWITHMOSTRECORDS_QUERY = "SELECT PatientID, COUNT(*) AS count FROM Record GROUP BY PatientID ORDER BY count DESC LIMIT 1;";
    public static final String GETFROMPATIENTID_QUERY = "SELECT * FROM Patient WHERE PatientID = ?;";
    public static final String GETDATEWITHMOREPATIENTS_QUERY =
            "SELECT DATE(DateAndTime) as Date, PatientID, COUNT(*) AS count " +
                    "FROM Appointment " +
                    "GROUP BY Date, PatientID " +
                    "ORDER BY count DESC LIMIT 1";
    public static final String RECORDSFROMUSERNAME_DOCTORSONLY_QUERY = "SELECT * FROM Record WHERE DoctorID = " +
            "(SELECT PatientOrDoctorID FROM User_Authentication WHERE Username = ? AND isTypePatient = 0);";
    public static final String RECORD_DELETE = "DELETE FROM Record WHERE RecordID = ?;";
    public static final String CREDENTIALS_DELETEBYPATIENTID =
            "DELETE FROM User_Authentication WHERE PatientOrDoctorID = ? " +
            "AND isTypePatient = 1;";
    public static final String RECORD_DELETEBYPATIENTID = "DELETE FROM Record WHERE PatientID = ?;";
    public static final String DELETEPAYMENTSFROMPATIENTID_QUERY =
            "DELETE FROM Payment " +
            "WHERE MedicationID IN (" +
                    "SELECT MedicationID FROM PrescribedMedication WHERE RecordID IN " +
                        "(SELECT RecordID FROM Record WHERE PatientID = ?));";
    public static final String DELETEMEDICATIONSFROMPATIENTID_QUERY =
            "DELETE FROM PrescribedMedication " +
            "WHERE RecordID IN (SELECT RecordID FROM Record WHERE PatientID = ?);";
    public static final String APP_DELETEBYPATIENTID = "DELETE FROM Appointment WHERE PatientID = ?;";
    public static final String COUNTMEDICATIONSFROMPATIENTID_QUERY = "SELECT COUNT(*) AS count FROM PrescribedMedication WHERE RecordID IN (SELECT RecordID FROM Record WHERE PatientID = ?);";
    public static final String GETDOCTORIDFROMUSERNAME_QUERY = "SELECT PatientOrDoctorID FROM User_Authentication WHERE Username = ? AND isTypePatient = 0;";
    public static final String RECORD_UPDATE = "UPDATE Record SET Diagnosis = ? WHERE RecordID = ?;";
    public static final String MEDICATION_UPDATE = "UPDATE PrescribedMedication SET Name = ?, Dosage = ? WHERE MedicationID = ?;";
}