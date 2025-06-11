package dao.impl;

import common.RestaurantError;
import dao.OrderItemDAO;
import dao.common.HqlQueries;
import dao.common.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import model.OrderItem;

import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {
    private final JPAUtil jpaUtil;
    private EntityManager entityManager;

    @Inject
    public OrderItemDAOImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<RestaurantError, List<OrderItem>> getAll(int orderId) {
        Either<RestaurantError, List<OrderItem>> either;
        List<OrderItem> orderItems;
        entityManager = jpaUtil.getEntityManager();

        try {
            orderItems = entityManager.createNamedQuery(HqlQueries.HQL_GET_ORDERITEMS_BY_ORDER, OrderItem.class)
                    .setParameter(HqlQueries.IDORDER, orderId)
                    .getResultList();
            either = Either.right(orderItems);

        } catch (PersistenceException e) {
            either = Either.left(new RestaurantError(0, e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> add(OrderItem orderItem) {
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.persist(orderItem);
            entityTransaction.commit();

            either = Either.right(1);

        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0, e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> add(List<OrderItem> orderItems) {
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            for (OrderItem orderItem : orderItems) {
                entityManager.persist(orderItem);
            }
            entityTransaction.commit();
            either = Either.right(1);

        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0, e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> update(OrderItem orderItem) {
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.merge(orderItem);
            entityTransaction.commit();

            either = Either.right(1);

        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0, e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> delete(OrderItem orderItem) {
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.remove(entityManager.merge(orderItem));
            entityTransaction.commit();

            either = Either.right(1);

        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0, e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> delete(List<OrderItem> orderItems) {
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            entityManager.createNamedQuery(HqlQueries.HQL_DELETE_ORDERITEMS_BY_ORDER)
                    .setParameter(HqlQueries.IDORDER, orderItems.get(0).getOrder().getId())
                    .executeUpdate();

            entityTransaction.commit();
            either = Either.right(1);
        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0, e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;

    }
}
