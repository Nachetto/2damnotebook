package com.hospitalcrud.dao.repository.hibernate;


import com.hospitalcrud.dao.connection.JPAUtil;
import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.CredentialDAO;
import com.hospitalcrud.dao.repository.PatientDAO;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("hibernate")
@Log4j2
public class PatientRepository implements PatientDAO {
    private final JPAUtil jpautil;
    private EntityManager em;

    public PatientRepository(JPAUtil jpautil, JPAUtil jpautil1) {
        this.jpautil = jpautil1;
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> list;
        try {
            em = jpautil.getEntityManager();
            list = em.createNamedQuery("Patient.getAll", Patient.class)
                    .getResultList();
        }catch (Exception e) {
            log.error("Error getting all patients", e);
            return null;
        }
        finally {
            if (em != null) em.close();
        }
        return list;
    }

    @Override
    public int save(Patient m) {
        int result = 0;
        try {
            em = jpautil.getEntityManager();
            em.getTransaction().begin();
            em.persist(m);
            em.getTransaction().commit();
            result = m.getId();
        }catch (Exception e) {
            log.error("Error saving patient", e);
            em.getTransaction().rollback();
        }
        finally {
            if (em != null) em.close();
        }
        return result;
    }

    @Override
    public void update(Patient m) {
        try {
            em = jpautil.getEntityManager();
            em.getTransaction().begin();
            em.merge(m);
            em.getTransaction().commit();
        }catch (Exception e) {
            log.error("Error updating patient", e);
            em.getTransaction().rollback();
        }
        finally {
            if (em != null) em.close();
        }
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        try {
            em = jpautil.getEntityManager();
            em.getTransaction().begin();
            Patient m = em.find(Patient.class, id);
            if (m != null) {
                if (confirmation) {
                    em.remove(m);
                    em.getTransaction().commit();
                    return true;
                }
            }
        }catch (Exception e) {
            log.error("Error deleting patient", e);
            em.getTransaction().rollback();
        }
        finally {
            if (em != null) em.close();
        }
        return false;
    }


    public Patient getById(int id) {
        Patient patient;
        em = jpautil.getEntityManager();
        try {
            patient = em.find(Patient.class, id);
        } finally {
            if (em != null) em.close();
        }
        return patient;
    }
}