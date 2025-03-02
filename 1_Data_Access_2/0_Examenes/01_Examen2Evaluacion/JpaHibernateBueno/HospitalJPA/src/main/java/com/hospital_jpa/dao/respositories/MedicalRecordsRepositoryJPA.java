package com.hospital_jpa.dao.respositories;

import com.hospital_jpa.dao.model.MedicalRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class MedicalRecordsRepositoryJPA implements com.hospital_jpa.dao.interfaces.MedicalRecordsRepository {

    private final JPAUtil jpaUtil;

    public MedicalRecordsRepositoryJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }


    @Override
    public List<MedicalRecord> getAll(int idPatient) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        try (EntityManager em = jpaUtil.getEntityManager()) {
            medicalRecords = em.createNamedQuery("getPatientMedicalRecords",MedicalRecord.class)
                    .setParameter("id", idPatient)
                    .getResultList();
            for (MedicalRecord medicalRecord :  medicalRecords) {
                medicalRecord.getMedications().size();
            }
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        }
        return medicalRecords;
    }

    @Override
    public void delete(MedicalRecord medicalRecord) {
        EntityTransaction tx = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.find(MedicalRecord.class, medicalRecord.getId()));
            tx.commit();
        }
        catch (Exception e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public int save(MedicalRecord medicalRecord) {
        EntityTransaction tx = null;
        medicalRecord.getMedications().forEach(m -> m.setMedicalRecord(medicalRecord));
        try (EntityManager em = jpaUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(medicalRecord);
            tx.commit();
        } catch (PersistenceException e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        }
        return medicalRecord.getId();
    }

    @Override
    public void update(MedicalRecord medicalRecord) {
        EntityTransaction tx = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.createNamedQuery("deletePrescribedMedications")
                    .setParameter("record_id", medicalRecord.getId())
                    .executeUpdate();
            em.merge(medicalRecord);
            tx.commit();
        } catch (Exception e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        }
    }
}
