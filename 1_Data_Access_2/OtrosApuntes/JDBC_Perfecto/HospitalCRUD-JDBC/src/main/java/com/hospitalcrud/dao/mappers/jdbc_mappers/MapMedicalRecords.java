package com.hospitalcrud.dao.mappers.jdbc_mappers;

import com.hospitalcrud.dao.model.MedicalRecord;
import com.hospitalcrud.dao.model.Medication;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MapMedicalRecords {
    public List<MedicalRecord> readRS(ResultSet rs) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        try {
            while (rs.next()) medicalRecords.add(new MedicalRecord(
                    rs.getInt("record_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getString("diagnosis"),
                    rs.getDate("admission_Date").toLocalDate()
            ));
            return medicalRecords;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Medication> compareMedications(ResultSet dataBaseMedications, List<Medication> medications) {
        List<Medication> addMedications = new ArrayList<>();
        List<String> medicationNames = medications.stream().map(Medication::getMedicationName).toList();
        List<Medication> deleteMedications = new ArrayList<>();
        try {
            while (dataBaseMedications.next()) {
                String dbMedication = dataBaseMedications.getString("medication_name");
                if (!medicationNames.contains(dbMedication))
                    addMedications.add(medications.stream().
                            filter(m -> m.getMedicationName().equals(dbMedication)).findFirst().get());
            }
            return addMedications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}