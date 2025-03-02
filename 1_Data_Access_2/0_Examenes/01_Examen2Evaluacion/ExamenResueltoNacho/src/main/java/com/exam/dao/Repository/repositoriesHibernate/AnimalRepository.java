package com.exam.dao.Repository.repositoriesHibernate;

import com.exam.dao.model.hibernateModel.AnimalHibernate;
import com.exam.dao.utils.hibernate.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class AnimalRepository {
    private final JPAUtil jpaUtil;

    @Inject
    public AnimalRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public AnimalHibernate get(String name) {
        AnimalHibernate finalAnimal = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            finalAnimal = em.createNamedQuery("AnimalHibernate.findByHabitat_Habitat", AnimalHibernate.class)
                    .setParameter("name", name).getResultList().getFirst();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return finalAnimal;
    }

    public void delete(){

    }
}
