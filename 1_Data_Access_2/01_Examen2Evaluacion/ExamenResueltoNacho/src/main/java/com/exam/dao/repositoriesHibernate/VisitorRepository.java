package com.exam.dao.repositoriesHibernate;

import com.exam.dao.model.hibernateModel.VisitorHibernate;
import com.exam.dao.utils.JPAUtil;
import com.exam.domain.error.InternalServerErrorException;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class VisitorRepository {

    private final JPAUtil jpaUtil;

    @Inject
    public VisitorRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }


    public int get(String name) {
        int finalID = -1;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            finalID = em.createNamedQuery("VisitorHibernate.findByName", Integer.class)
                    .setParameter("name",name).getResultList().getFirst();
        } catch (PersistenceException e) {
            throw new InternalServerErrorException("error al sacar el id desde un nombre: "+e.getMessage());
        }
        return finalID;
    }

    public VisitorHibernate get(int id) {
        VisitorHibernate finalvisitor = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            finalvisitor = em.createNamedQuery("VisitorHibernate.findById", VisitorHibernate.class)
                    .setParameter("id",id).getResultList().getFirst();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return finalvisitor;
    }
}
