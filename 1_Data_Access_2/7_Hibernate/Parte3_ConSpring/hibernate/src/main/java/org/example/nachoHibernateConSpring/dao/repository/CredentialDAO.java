package org.example.nachoHibernateConSpring.dao.repository;

import org.example.nachoHibernateConSpring.dao.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialDAO extends JpaRepository<Credential, Integer> {
    List<Credential> findByUsername(String username);
    List<Credential> findByUsernameAndPassword(String username, String password);
}
