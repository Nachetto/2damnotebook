package org.example.nachoHibernateConSpring.dao.repository;

import org.example.nachoHibernateConSpring.dao.model.Credential;
import org.example.nachoHibernateConSpring.dao.model.Patient;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CredentialDAO extends JpaRepository<Credential, Integer> {
    List<Credential> findByUsername(String username);

    @Modifying
    @Transactional(noRollbackFor = DataIntegrityViolationException.class)
    void deleteByPatient(Patient patient);
}
