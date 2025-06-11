package org.example.nachoHibernateConSpring.domain.model;

import lombok.*;
import org.example.nachoHibernateConSpring.dao.model.MedRecord;
import org.example.nachoHibernateConSpring.dao.model.Medication;
import org.example.nachoHibernateConSpring.service.DoctorService;
import org.example.nachoHibernateConSpring.service.PatientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedRecordUI {

    private int id;
    private int idPatient;
    private int idDoctor;
    private String description;
    private String date;
    private List<String> medications;

    public MedRecord toMedRecord(PatientService patientService, DoctorService doctorService) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate datee = LocalDate.parse(this.date, formatter);
        List<Medication> medicationsFinal = new ArrayList<>();
        medications.forEach(medication -> {
            Medication med = new Medication();
            med.setMedicationName(medication);
            medicationsFinal.add(med);
        });

        return new MedRecord(id, patientService.getById(idPatient), doctorService.getDoctor(idDoctor), description, datee, medicationsFinal);
    }
}

