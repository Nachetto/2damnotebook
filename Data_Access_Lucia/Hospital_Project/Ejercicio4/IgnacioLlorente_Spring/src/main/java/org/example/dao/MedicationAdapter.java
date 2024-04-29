package org.example.dao;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.example.domain.PrescribedMedication;
import org.example.domain.xml.MedicationXML;

public class MedicationAdapter extends XmlAdapter<MedicationXML, PrescribedMedication> {
    @Override
    public PrescribedMedication unmarshal(MedicationXML medicationXML) {
        return new PrescribedMedication(medicationXML.getMedicationID(),
                medicationXML.getName(), medicationXML.getDosage(), -1);
    }

    public PrescribedMedication unmarshal(MedicationXML medicationXML, int recordID) {
        return new PrescribedMedication(medicationXML.getMedicationID(),
                medicationXML.getName(), medicationXML.getDosage(), recordID);
    }

    @Override
    public MedicationXML marshal(PrescribedMedication prescribedMedication) {
        return new MedicationXML(prescribedMedication.getMedicationID(),
                prescribedMedication.getName(), prescribedMedication.getDosage());
    }
}
