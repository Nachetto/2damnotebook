package com.hospital_jpa.dao.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecord {
    @SerializedName("_id")
    private ObjectId id;
    private LocalDate date;
    private String diagnosis;
    private ObjectId doctor;
    private List<String> medications;
    private ObjectId patient;


    public MedicalRecord(String date, String diagnosis, ObjectId doctor, List<String> medications, ObjectId patient) {
        this.date = LocalDate.parse(date);
        this.diagnosis = diagnosis;
        this.doctor = doctor;
        this.medications = medications;
        this.patient = patient;
    }

    public MedicalRecord(ObjectId medicalRecordId, String date, String diagnosis, ObjectId doctor, List<String> medications, ObjectId patient) {
        this.id = medicalRecordId;
        this.date = LocalDate.parse(date);
        this.diagnosis = diagnosis;
        this.doctor = doctor;
        this.medications = medications;
        this.patient = patient;
    }
}
