package com.hospitalcrud.dao.connection;

import jakarta.inject.Singleton;

@Singleton
public class JPAUtil {

    private EntityManagerFactory emf;

    public JPAUtil() {
        emf = getEmf();
    }

    private EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT_NAME);
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}