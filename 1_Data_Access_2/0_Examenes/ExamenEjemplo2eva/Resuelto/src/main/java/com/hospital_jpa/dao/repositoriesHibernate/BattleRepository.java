package com.hospital_jpa.dao.repositoriesHibernate;

import com.hospital_jpa.domain.model.hibernateModel.BattleHibernate;
import com.hospital_jpa.dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;


import java.util.ArrayList;
import java.util.List;


public class BattleRepository {
    private final JPAUtil jpaUtil;

    @Inject
    public BattleRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public List<BattleHibernate> getAll() {
        List<BattleHibernate> battleHibernates =new ArrayList<>();
        try (EntityManager em = jpaUtil.getEntityManager()) {
            battleHibernates = em.createNamedQuery("getAllBattle", BattleHibernate.class).getResultList();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return battleHibernates;
    }
}
