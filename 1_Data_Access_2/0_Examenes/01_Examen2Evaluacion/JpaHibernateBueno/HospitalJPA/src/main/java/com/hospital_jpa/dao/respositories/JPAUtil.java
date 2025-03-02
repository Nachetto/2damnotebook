package com.hospital_jpa.dao.respositories;

import com.hospital_jpa.common.Constantes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Component;

@Component
public class JPAUtil {
    private final EntityManagerFactory emf;

    public JPAUtil() {
        emf=getEmf();
    }

    private EntityManagerFactory getEmf() {
        return Persistence.createEntityManagerFactory(Constantes.PERSISTANCE_UNIT);
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
