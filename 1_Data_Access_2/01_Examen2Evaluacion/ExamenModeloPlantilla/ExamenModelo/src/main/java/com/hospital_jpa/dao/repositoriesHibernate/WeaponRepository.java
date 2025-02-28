package com.hospital_jpa.dao.repositoriesHibernate;

import com.hospital_jpa.domain.model.hibernateModel.WeaponHibernate;
import com.hospital_jpa.dao.utils.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.exception.ConstraintViolationException;

public class WeaponRepository {

    private final JPAUtil jpaUtil;

    @Inject
    public WeaponRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public int save(WeaponHibernate weapon) {
        EntityTransaction tx = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.persist(weapon);
            tx.commit();
            return weapon.getId();
        } catch (ConstraintViolationException e) {
            tx.rollback();
        } catch (Exception e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
        }
        return -1;
    }


    public void delete(int id) {
        EntityTransaction tx = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.find(WeaponHibernate.class, id));
            tx.commit();
        }
        catch (Exception e) {
            assert tx != null;
            if (tx.isActive()) tx.rollback();
        }
    }

}
