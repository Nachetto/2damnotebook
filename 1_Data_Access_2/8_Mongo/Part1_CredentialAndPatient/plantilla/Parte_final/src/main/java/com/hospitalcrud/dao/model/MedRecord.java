package com.hospitalcrud.dao.model;

import com.google.gson.annotations.SerializedName;
import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.service.MedicationService;
import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedRecord {
    @SerializedName("_id")
    private ObjectId id;
    private LocalDate date;
    private ObjectId patientId;
    private ObjectId doctorId;
    private String diagnosis;
    private List<Medication> medications;
}