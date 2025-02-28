package com.exam.dao.repositoriesHibernate;

import com.exam.dao.model.hibernateModel.VisitorHibernate;
import com.exam.dao.utils.JPAUtil;
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
            finalID = em.createNamedQuery("VisitorHibernate.findByName", VisitorHibernate.class)
                    .setParameter("name",name).getFirstResult();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return finalID;
    }

    public VisitorHibernate get(int id) {
        VisitorHibernate finalvisitor = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            finalvisitor = em.createNamedQuery("VisitorHibernate.findById", VisitorHibernate.class)
                    .setParameter("id",id).getSingleResult();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return finalvisitor;
    }
}
