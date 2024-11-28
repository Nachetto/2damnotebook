package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.common.Constants;
import org.example.dao.ClientDAO;
import org.example.model.ClientsEntity;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public class ClientDaoImpl implements ClientDAO {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public ClientDaoImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<GymError, ClientsEntity> get(String name) {
        Either<GymError, ClientsEntity> result = null;
        em = jpaUtil.getEntityManager();
        try {
            List<ClientsEntity> client = em.createNamedQuery("selectClientByName")
                    .setParameter("clientName", name)
                    .getResultList();
            result = Either.right(client.get(0));
        } catch (Exception e) {
            result = Either.left(new GymError("Error updating client"));
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    @Override
    public Either<GymError, ClientsEntity> get(int id) {
        Either<GymError, ClientsEntity> result = null;
        em = jpaUtil.getEntityManager();
        try {
            ClientsEntity client = em.find(ClientsEntity.class, id);
            result = Either.right(client);
        } catch (Exception e) {
            result = Either.left(new GymError("Error updating client"));
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    @Override
    public Either<GymError, ClientsEntity> update(String name, int price) {
        Either<GymError, ClientsEntity> result = null;
        em = jpaUtil.getEntityManager();
        try {
            List<ClientsEntity> client = em.createNamedQuery("selectClientByName")
                    .setParameter("clientName", name)
                    .getResultList();
            ClientsEntity clients = client.get(0);

            clients.setBalance(clients.getBalance() - price);
            if (clients.getBalance() <= 0) {
                result = Either.left(new GymError("Not enough money"));
            } else {
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.merge(clients);
                tx.commit();
                result = Either.right(clients);
            }
        } catch (Exception e) {
            result = Either.left(new GymError("Error updating client"));
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    @Override
    public Either<GymError, Integer> delete(ClientsEntity client, boolean deleteSubscription) {
        Either<GymError, Integer> result = null;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (deleteSubscription){
                List<SubscriptionsEntity> subscriptions = em.createNamedQuery("getSubscriptionsByClient")
                        .setParameter("nameClient", client.getId())
                        .getResultList();
                for (SubscriptionsEntity subscription : subscriptions){
                    em.remove(em.merge(subscription));
                }
            }
            em.remove(em.merge(client));
            tx.commit();
            result = Either.right(0);

        }catch (Exception ex) {
            if (ex.getMessage().contains(Constants.FOREIGN_KEY_CONSTRAINT_FAILS)) {
                result = Either.left(new GymError("The client has subscriptions"));
            } else {
                result = Either.left(new GymError("Error deleting client"));
            }
        } finally {
            if (em != null) em.close();
        }


        return result;
    }


}
