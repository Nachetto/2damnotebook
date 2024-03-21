package org.example.domain;

import lombok.Data;

@Data
public class Doctor {
    private int doctorID;
    private String name;
    private String specialty;
    private String contactDetails;

    // Constructor, getters y setters

    public Doctor(int doctorID, String name, String specialty, String contactDetails) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialty = specialty;
        this.contactDetails = contactDetails;
    }

    public Doctor(String fileLine) {
        String[] elemArray = fileLine.split(";");
        this.doctorID = Integer.parseInt(elemArray[0]);
        this.name = elemArray[1];
        this.specialty = elemArray[2];
        this.contactDetails = elemArray[3];
    }

    public String toStringTextFile() {
        return doctorID + ";" + name + ";" + specialty + ";" + contactDetails;
    }
}
