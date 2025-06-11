package com.hospitalcrud.domain.model;

import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.service.DoctorService;
import com.hospitalcrud.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class MedRecordUI {

    private int id;
    private String description;
    private String date;
    private int idPatient;
    private int idDoctor;
    private List<String> medications;


    public MedRecord toMedRecord(PatientService patientService, DoctorService doctorService, ObjectId objectId) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate datee = LocalDate.parse(this.date, formatter);
        //create the medications
        List<Medication> medicationsFinal = new ArrayList<>();
        medications.forEach(medication -> {
            Medication med = new Medication();
            med.setName(medication);
            medicationsFinal.add(med);
        });

        return new MedRecord(objectId, datee, patientService.getById(idPatient).getId(), doctorService.getDoctorObjectId(idDoctor), description, medicationsFinal);
    }
}

