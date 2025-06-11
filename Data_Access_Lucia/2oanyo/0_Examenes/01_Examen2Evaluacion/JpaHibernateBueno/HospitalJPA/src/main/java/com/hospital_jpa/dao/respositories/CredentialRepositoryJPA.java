package com.hospital_jpa.dao.respositories;

import com.hospital_jpa.dao.model.Credential;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class CredentialRepositoryJPA implements com.hospital_jpa.dao.interfaces.CredentialRepository {

    private final JPAUtil jpaUtil;

    public CredentialRepositoryJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Credential> getAll() {
        return List.of();
    }

    @Override
    public boolean delete(int patient_id) {
        return false;
    }

    @Override
    public void update(Credential credential) {

    }

    @Override
    public Credential get(String username) {
        Credential c = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            c = em.createNamedQuery("GET_CREDENTIAL",Credential.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        }
        return c;
    }
}
