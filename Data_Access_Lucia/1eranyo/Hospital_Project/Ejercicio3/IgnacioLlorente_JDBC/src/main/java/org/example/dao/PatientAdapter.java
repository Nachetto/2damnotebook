package org.example.dao;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.example.domain.Credential;
import org.example.domain.Patient;
import org.example.domain.xml.PatientXML;

public class PatientAdapter extends XmlAdapter<PatientXML, Patient> {
    @Override
    public Patient unmarshal(PatientXML patientXML) {
        return new Patient(patientXML.getPatientID(), patientXML.getName(),
                patientXML.getContactDetails(), patientXML.getPersonalInformation(),
                new Credential(patientXML.getUsername(), patientXML.getPassword()));
    }

    @Override
    public PatientXML marshal(Patient patient) {
        return new PatientXML(patient.getPatientID(), patient.getName(),
                patient.getContactDetails(), patient.getPersonalInformation(),
                patient.getCredential().username(), patient.getCredential().password());}
}
