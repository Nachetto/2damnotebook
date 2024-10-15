package org.example.dao.impl;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Singleton
public class JPAUtil {

    private EntityManagerFactory emf;

    public JPAUtil() {
       emf=getEmf();
    }

    private EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory("restaurant.hibernate");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
