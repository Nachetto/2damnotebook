package com.hospitalcrud.dao.repository.hibernate;


import com.hospitalcrud.dao.connection.JPAUtil;
import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repository.CredentialDAO;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("hibernate")
@Log4j2
public class CredentialRepository implements CredentialDAO {
    private final JPAUtil jpautil;
    private EntityManager em;

    public CredentialRepository(JPAUtil jpautil) {
        this.jpautil = jpautil;
    }

    public boolean validateUsername(String username) {
        EntityManager em = jpautil.getEntityManager();
        try {
            Long count = em.createNamedQuery("Credential.validate_username", Long.class) // Cant be an integer :(
                    .setParameter("username", username)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            log.error("Error validating username: {}", username, e);
            return false;
        } finally {
            if (em != null) em.close();
        }
    }

//    public boolean login(String username, String password) {
//        List<Credential> list;
//        try {
//            em = jpautil.getEntityManager();
//            list = em.createNamedQuery("Credential.login", Credential.class)
//                    .setParameter("username", username)
//                    .setParameter("password", password)
//                    .getResultList();
//        }catch (Exception e) {
//            log.error("Error during login for username: {}", username, e);
//            return false;
//        } finally {
//            if (em != null) em.close();
//        }
//        return !list.isEmpty();
//    }

    public boolean login(String username, String password) {
        List<Credential> list;
        try {
            em = jpautil.getEntityManager();
            list = em.createNamedQuery("Credential.login", Credential.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();
            return !list.isEmpty();
        } catch (Exception e) {
            log.error("Error during login for username: {}", username, e);
            return false;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public List<Credential> getAll() {
        List<Credential> list;
        try {
            em = jpautil.getEntityManager();
            list = em.createNamedQuery("Credential.getAll", Credential.class).getResultList();
        }catch (Exception e) {
            log.error("Error getting all credentials", e);
            list = List.of();
        }finally {
            if (em != null) em.close();
        }
        return list;
    }

    @Override
    public int save(Credential c) {
        //not used here

        return 0;}


    @Override
    public boolean delete(int id) {
        return false;
    }


    @Override
    public void update(Credential c) {
    }
}
