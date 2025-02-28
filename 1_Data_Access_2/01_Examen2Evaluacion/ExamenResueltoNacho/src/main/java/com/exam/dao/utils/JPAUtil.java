package com.exam.dao.utils;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class JPAUtil {
    private final EntityManagerFactory emf;

    public JPAUtil() {
        emf = getEmf();
    }

    private EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory("hibernate");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}