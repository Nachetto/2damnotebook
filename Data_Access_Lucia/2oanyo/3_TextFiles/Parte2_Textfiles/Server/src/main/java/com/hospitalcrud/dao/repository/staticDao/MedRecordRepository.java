package com.hospitalcrud.dao.repository.staticDao;

import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.MedRecordDAO;
import com.hospitalcrud.domain.error.BadRequestException;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import com.hospitalcrud.domain.error.NotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("txt")
public class MedRecordRepository implements MedRecordDAO {
    //creando la lista estatica de medrecords
    private static List<MedRecord> medRecords = new ArrayList<>(List.of(
            new MedRecord(1, 1, 1, "Gripe", LocalDate.now(), List.of(new Medication(1, "Paracetamol", 1))),
            new MedRecord(2, 2, 2, "Fiebre", LocalDate.now(), List.of(new Medication(2, "Ibuprofeno", 2))),
            new MedRecord(3, 3, 3, "Infeccion", LocalDate.now(), List.of(new Medication(3, "Amoxicilina", 3))),
            new MedRecord(4, 4, 4, "Alergia", LocalDate.now(), List.of(new Medication(4, "Dexametasona", 4)))
    ));

    @Override
    public List<MedRecord> getAll() {
        return medRecords;
    }

    @Override
    public int save(MedRecord m) {
        if (m == null) {
            throw new BadRequestException("MedRecord cannot be null");
        }
        try {
            if (medRecords.stream().anyMatch(medRecord -> medRecord.getId() == m.getId())) {
                throw new BadRequestException("MedRecord with ID " + m.getId() + " already exists");
            }
            medRecords.add(MedRecord.builder()
                    .id(medRecords.size() + 1)
                    .idPatient(m.getIdPatient())
                    .idDoctor(m.getIdDoctor())
                    .diagnosis(m.getDiagnosis())
                    .date(m.getDate())
                    .medications(m.getMedications().stream().map(medication -> Medication.builder()
                            .id(medication.getId())
                            .medicationName(medication.getMedicationName())
                            .medRecordId(m.getId())
                            .build()).toList())
                    .build());
            return medRecords.size();
        } catch (Exception e) {
            throw new InternalServerErrorException("Error saving MedRecord");
        }
    }

    @Override
    public void update(MedRecord m) {
        if (m == null) {
            throw new BadRequestException("MedRecord cannot be null");
        }
        try {
            for (int i = 0; i < medRecords.size(); i++) {
                if (medRecords.get(i).getId() == m.getId()) {
                    medRecords.set(i, m);
                    return;
                }
            }
            throw new NotFoundException("MedRecord not found");
        } catch (Exception e) {
            throw new InternalServerErrorException("Error updating MedRecord");
        }
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        if (!confirmation) {
            throw new BadRequestException("Confirmation is required to delete MedRecord");
        }
        try {
            if (medRecords.removeIf(medRecord -> medRecord.getId() == id)) {
                return true;
            }
            throw new NotFoundException("MedRecord not found");
        } catch (Exception e) {
            throw new InternalServerErrorException("Error deleting MedRecord");
        }
    }

    public List<MedRecord> get(int patientId) {
        return medRecords.stream().filter(medRecord -> medRecord.getIdPatient() == patientId).toList();
    }
}