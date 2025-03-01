package com.exam.dao.repositoriesHibernate;


import com.exam.dao.model.hibernateModel.AnimalHibernate;
import com.exam.dao.model.hibernateModel.AnimalVisitsHibernate;
import com.exam.dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnimalVisitsRepository {
    private AnimalVisitsHibernate animalVisitsHibernate;
    private final JPAUtil jpaUtil;
    @Inject
    public AnimalVisitsRepository(AnimalVisitsHibernate animalVisitsHibernate, JPAUtil jpaUtil){
        this.animalVisitsHibernate=animalVisitsHibernate;
        this.jpaUtil = jpaUtil;
    }
    public void add(int visitorID, AnimalHibernate animalID, Date from) {
        AnimalVisitsHibernate visitToAdd= new AnimalVisitsHibernate(visitorID,animalID,from);
        EntityTransaction tx = null;
        EntityManager em = null;
        try {
            em = jpaUtil.getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.merge(visitToAdd);
            tx.commit();
        } catch (ConstraintViolationException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<AnimalVisitsHibernate> getAll() {
        List<AnimalVisitsHibernate> patients = new ArrayList<>();
        try (EntityManager em = jpaUtil.getEntityManager()) {
            patients = em.createNamedQuery("AnimalVisitsHibernate.findAll", AnimalVisitsHibernate.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println(e.getMessage() + e);
        }
        return patients;
    }
}
