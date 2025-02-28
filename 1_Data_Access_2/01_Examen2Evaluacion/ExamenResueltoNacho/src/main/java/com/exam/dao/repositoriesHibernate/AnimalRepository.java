package com.exam.dao.repositoriesHibernate;

import com.exam.dao.model.hibernateModel.AnimalHibernate;
import com.exam.dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class AnimalRepository {
    private final JPAUtil jpaUtil;

    @Inject
    public AnimalRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public AnimalHibernate get(int habitatID) {
        AnimalHibernate finalAnimal = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            finalAnimal = em.createNamedQuery("AnimalHibernate.findByHabitat_HabitatID", AnimalHibernate.class)
                    .setParameter("habitatID", habitatID)
                    .getSingleResult();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return finalAnimal;
    }

    public void delete(){

    }
}
