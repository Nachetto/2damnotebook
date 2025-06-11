package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.example.dao.ClientDAO;
import org.example.dao.SubscriptionsDAO;
import org.example.model.ClientsEntity;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscriptionsDaoImpl implements SubscriptionsDAO {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public SubscriptionsDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }


    @Override
    public Either<GymError, List<SubscriptionsEntity>> getAll(int idCliente) {
        Either<GymError, List<SubscriptionsEntity>> result = null;
        em = jpaUtil.getEntityManager();
        try {
            List<SubscriptionsEntity> subscriptionsEntities = em.createNamedQuery("getSubscriptionsAndServicesByClient")
                    .setParameter("nameClient", idCliente)
                    .getResultList();
            result = Either.right(subscriptionsEntities);

        } catch (Exception e) {
            result = Either.left(new GymError("Error  subscripciones"));
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    @Override
    public Either<GymError, SubscriptionsEntity> get(int id) {
        Either<GymError, SubscriptionsEntity> result = null;
        em = jpaUtil.getEntityManager();
        try {
            List<SubscriptionsEntity> subscriptionsEntities = em.createNamedQuery("getSubscriptionsByClient")
                    .setParameter("nameClient", 2)
                    .getResultList();
            List<SubscriptionsEntity> activeSubscriptions = new ArrayList<>();
            Date now = new Date();
            for (SubscriptionsEntity subscription : subscriptionsEntities) {
                if (subscription.getEndDate().after(now) && subscription.getStartDate().before(now)) {
                    activeSubscriptions.add(subscription);
                }
            }
            if (activeSubscriptions.isEmpty()) {
                result = Either.left(new GymError("No subscriptions"));
            } else {
                result = Either.right(activeSubscriptions.get(0));
            }
        } catch (Exception e) {
            result = Either.left(new GymError("Error  subscripciones"));
        } finally {
            if (em != null) em.close();
        }
        return result;
    }
}
