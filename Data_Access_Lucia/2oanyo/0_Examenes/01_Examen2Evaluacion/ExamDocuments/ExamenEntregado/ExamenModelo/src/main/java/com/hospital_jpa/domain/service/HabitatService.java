package com.hospital_jpa.domain.service;

import com.hospital_jpa.dao.model.hibernateModel.HabitatHibernate;
import com.hospital_jpa.dao.model.hibernateModel.VisitorHibernate;
import com.hospital_jpa.dao.repositoriesHibernate.HabitatRepository;
import com.hospital_jpa.dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public class HabitatService {
    private final HabitatRepository repository;
    private final JPAUtil jpaUtil;
    @Inject
    public HabitatService(HabitatRepository repository, JPAUtil jpaUtil){
        this.repository=repository;
        this.jpaUtil = jpaUtil;
    }
    public int get(String habitatName) {
        int finalID = -1;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            finalID = em.createNamedQuery("HabitatHibernate.findByName", HabitatHibernate.class)
                    .setParameter("name",habitatName).getFirstResult();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return finalID;
    }
}
