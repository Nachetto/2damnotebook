package org.example.domain;

import lombok.Data;

@Data
public class Record {
    private int recordID;
    private int patientID;
    private String diagnosis;
    private int doctorID;

    // Constructor, getters y setters
    public Record(int recordID, int patientID, String diagnosis, int doctorID) {
        this.recordID = recordID;
        this.patientID = patientID;
        this.diagnosis = diagnosis;
        this.doctorID = doctorID;
    }

    public Record(String fileLine) {
        String[] elemArray = fileLine.split(";");
        this.recordID = Integer.parseInt(elemArray[0]);
        this.patientID = Integer.parseInt(elemArray[1]);
        this.diagnosis = elemArray[2];
        this.doctorID = Integer.parseInt(elemArray[3]);
    }

    public String toStringTextFile() {
        return recordID + ";" + patientID + ";" + diagnosis + ";" + doctorID;
    }

    @Override
    public String toString() {
        return "\n\nRecord with id: " + recordID +
                "\nPatient ID: " + patientID +
                "\nDiagnosis: " + diagnosis +
                "\nDoctor ID: " + doctorID;
    }
}
