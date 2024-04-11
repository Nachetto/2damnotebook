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

}