package dao.common;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Singleton
public class JPAUtil {
    private EntityManagerFactory entityManagerFactory;
    public JPAUtil(){
        entityManagerFactory = getEntityManagerFactory();
    }
    private EntityManagerFactory getEntityManagerFactory(){
        return Persistence.createEntityManagerFactory(SqlQueries.UNIT_3_HIBERNATE);
    }
    public EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
