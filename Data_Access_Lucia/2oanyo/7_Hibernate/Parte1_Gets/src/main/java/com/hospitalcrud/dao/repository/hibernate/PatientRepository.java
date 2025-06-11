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
        return 0;
    }

    @Override
    public void update(Patient m) {

    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }
}