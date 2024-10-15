package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.dao.SubscriptionsDAO;
import org.example.dao.SubscriptionsServiceDAO;
import org.example.model.SubscriptionServicesEntity;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscriptionsServicesDaoImpl implements SubscriptionsServiceDAO {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public SubscriptionsServicesDaoImpl(JPAUtil jpaUtil){
        this.jpaUtil = jpaUtil;
    }


    @Override
    public Either<GymError, SubscriptionServicesEntity> save(SubscriptionServicesEntity subscriptionServices) {
        Either<GymError, SubscriptionServicesEntity> result;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try{
            tx.begin();
            em.persist(subscriptionServices);
            tx.commit();
            return Either.right(subscriptionServices);
        } catch (Exception e) {
            result = Either.left(new GymError("Error adding subscription to service."));
        } finally {
            if (em != null) em.close();
        }
        return result;
    }
}
