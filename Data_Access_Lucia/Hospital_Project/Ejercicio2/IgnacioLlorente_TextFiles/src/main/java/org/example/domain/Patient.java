package org.example.domain;

import lombok.Data;

@Data
public class Patient {
    private final int patientID;
    private final String name;
    private final String contactDetails;
    private final String personalInformation;
    private final Credential credential;

    public Patient(int patientID, String name, String contactDetails, String personalInformation, Credential credential) {
        this.patientID = patientID;
        this.name = name;
        this.contactDetails = contactDetails;
        this.personalInformation = personalInformation;
        this.credential=credential;
    }

    public Patient(String fileLine) {
        String[] elemArray = fileLine.split(";");
        this.patientID = Integer.parseInt(elemArray[0]);
        this.name = elemArray[1];
        this.contactDetails = elemArray[2];
        this.personalInformation = elemArray[3];
        this.credential = new Credential(elemArray[4], elemArray[5]);
    }

    public String toStringTextFile() {
        return patientID + ";" + name + ";" + contactDetails + ";" + personalInformation+ ";" + credential.username()+ ";" + credential.password();
    }

    @Override
    public String toString() {
        return "Patient{\n" +
                "patientID=" + patientID +
                "\nname='" + name + '\'' +
                "\ncontactDetails='" + contactDetails + '\'' +
                "\npersonalInformation='" + personalInformation + '\'' +
                "\ncredential=" + credential +
                "\n}\n";
    }

    // Constructor, getters y setters
}
