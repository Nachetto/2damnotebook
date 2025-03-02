package com.hospital_jpa.domain.mappers;

import com.hospital_jpa.dao.model.Patient;
import com.hospital_jpa.dao.model.Payment;
import com.hospital_jpa.domain.model.PatientUI;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class PatientMappers {

    public PatientUI toPatientUI (Patient patient, int id) {
        return new PatientUI(
                id,
                patient.getName(),
                patient.getBirthDate(),
                patient.getPayments().stream().mapToInt(Payment::getAmount).sum(),
                patient.getPhone()
        );
    }

    public Patient toPatient (PatientUI patient) {
        return new Patient(
                patient.getName(),
                patient.getBirthDate(),
                patient.getPhone()
        );
    }

    public Patient toPatient (PatientUI patient, ObjectId id) {
        return new Patient(
                id,
                patient.getName(),
                patient.getBirthDate(),
                patient.getPhone()
        );
    }
}
