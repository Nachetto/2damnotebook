package org.example.domain;

import lombok.Data;

@Data
public class PrescribedMedication {
    private int medicationID;
    private String name;
    private String dosage;
    private int recordID;

    // Constructor, getters y setters
    public PrescribedMedication(int medicationID, String name, String dosage, int recordID) {
        this.medicationID = medicationID;
        this.name = name;
        this.dosage = dosage;
        this.recordID = recordID;
    }

    public PrescribedMedication(String fileLine) {
        String[] elemArray = fileLine.split(";");
        this.medicationID = Integer.parseInt(elemArray[0]);
        this.name = elemArray[1];
        this.dosage = elemArray[2];
        this.recordID = Integer.parseInt(elemArray[3]);
    }

    public String toStringTextFile() {
        return medicationID + ";" + name + ";" + dosage + ";" + recordID;
    }
}
