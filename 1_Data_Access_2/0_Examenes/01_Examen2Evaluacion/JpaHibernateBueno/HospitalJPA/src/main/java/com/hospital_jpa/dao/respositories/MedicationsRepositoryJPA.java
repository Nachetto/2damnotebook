package com.hospital_jpa.dao.respositories;

import com.hospital_jpa.dao.model.MedicalRecord;
import com.hospital_jpa.dao.model.Medication;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class MedicationsRepositoryJPA implements com.hospital_jpa.dao.interfaces.MedicationsRepository {

    private final JPAUtil jpaUtil;

    public MedicationsRepositoryJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Medication> getPrescribedMedications(int medicalRecordId) {
        return List.of();
    }

    @Override
    public List<Medication> getAll() {
        List<Medication> medications = new ArrayList<>();
        try (EntityManager em = jpaUtil.getEntityManager()) {
            medications = em.createNamedQuery("getAll",Medication.class).getResultList();
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        }
        return medications;
    }

    @Override
    public void deletePatientMedications(int patientId) {

    }

    @Override
    public void deleteMedicalRecordMedications(int medicalRecordId) {

    }

    @Override
    public void save(MedicalRecord medicalRecord) {

    }

    @Override
    public void update(MedicalRecord medicalRecord) {

    }
}
