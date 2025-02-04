package org.example.nachoHibernateConSpring.dao.repository;

import org.example.nachoHibernateConSpring.dao.model.Session;
import org.springframework.stereotype.Repository;


public interface SessionDao {
    int save(Session session);
    Session load();
}
