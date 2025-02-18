package com.hospitalcrud.domain.model;

import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.service.DoctorService;
import com.hospitalcrud.service.PatientService;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public MedRecord toMedRecord(PatientService patientService, DoctorService doctorService, ObjectId objectId) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate datee = LocalDate.parse(this.date, formatter);
        //create the medications
        List<Medication> medicationsFinal = null;
        medications.forEach(medication -> {
            Medication med = new Medication();
            med.setName(medication);
            medicationsFinal.add(med);
        });

        return new MedRecord(objectId, datee, patientService.getById(idPatient).getId(), doctorService.getDoctor(idDoctor).getId(), description, medicationsFinal);
    }
}

