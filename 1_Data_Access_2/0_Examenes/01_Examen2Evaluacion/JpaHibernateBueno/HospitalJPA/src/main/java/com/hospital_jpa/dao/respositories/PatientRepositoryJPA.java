package com.hospital_jpa.dao.respositories;

import com.hospital_jpa.dao.model.Credential;
import com.hospital_jpa.dao.model.MedicalRecord;
import com.hospital_jpa.dao.model.Patient;
import com.hospital_jpa.domain.error.DUPLICATED_USERNAME;
import com.hospital_jpa.domain.error.FOREIGN_KEY_ERROR;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.RollbackException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Repository
@Log4j2
public class PatientRepositoryJPA implements com.hospital_jpa.dao.interfaces.PatientRepository {
    private final JPAUtil jpaUtil;

    public PatientRepositoryJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        try (EntityManager em = jpaUtil.getEntityManager()) {
            patients = em.createNamedQuery("getAllPatients", Patient.class).getResultList();
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        }
        return patients;
    }

    @Override
    public int save(Patient patient) {

        Credential credential = patient.getCredential();
        credential.setPatient(patient);

        EntityTransaction tx = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(patient);
            tx.commit();
        } catch (ConstraintViolationException e) {
            tx.rollback();
            log.error(e.getMessage(), e);
            throw new DUPLICATED_USERNAME();
        } catch (Exception e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        }
        return patient.getId();
    }

    @Override
    public void update(Patient patient) {
        EntityTransaction tx = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.merge(patient);
            tx.commit();
        } catch (Exception e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int patientId, boolean confirmation) {
        final String PATIENT_ID = "patient_id";
        EntityTransaction tx = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            if (confirmation) {
                List<MedicalRecord> records = em.createQuery(
                                "SELECT m FROM MedicalRecord m WHERE m.patient.id = :patient_id", MedicalRecord.class)
                        .setParameter(PATIENT_ID, patientId)
                        .getResultList();
                for (MedicalRecord r : records) {
                    em.remove(r);
                }
                em.createQuery("delete from Appointment app where app.patient.id = :patient_id")
                        .setParameter(PATIENT_ID, patientId)
                        .executeUpdate();
                em.createQuery("delete from Payment pay where pay.patient.id = :patient_id")
                        .setParameter(PATIENT_ID, patientId)
                        .executeUpdate();
            }
            em.remove(em.find(Patient.class, patientId));
            tx.commit();
        } catch (ConstraintViolationException e) {
            throw new FOREIGN_KEY_ERROR();
        } catch (Exception e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        }
    }
}
