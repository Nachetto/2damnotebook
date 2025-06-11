package com.exam.dao.Repository.repositoriesHibernate;


import com.exam.dao.model.hibernateModel.AnimalHibernate;
import com.exam.dao.model.hibernateModel.AnimalVisitsHibernate;
import com.exam.dao.utils.hibernate.JPAUtil;
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
        try (EntityManager em = jpaUtil.getEntityManager()){
            tx = em.getTransaction();
            tx.begin();
            em.merge(visitToAdd);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
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
