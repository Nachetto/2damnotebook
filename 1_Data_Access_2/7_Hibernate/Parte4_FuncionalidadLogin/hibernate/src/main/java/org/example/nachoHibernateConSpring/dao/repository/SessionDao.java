package org.example.nachoHibernateConSpring.dao.repository;

import org.example.nachoHibernateConSpring.dao.model.Credential;
import org.example.nachoHibernateConSpring.dao.model.Session;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDao {
    int save(Credential credential);
    Session load();
}
