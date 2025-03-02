package dao.impl;

import common.RestaurantError;
import dao.CustomerDAO;
import dao.common.HqlQueries;
import dao.common.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import model.Credentials;
import model.Customer;
import model.RestaurantOrder;

import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    private final JPAUtil jpaUtil;
    private EntityManager entityManager;

    @Inject
    public CustomerDAOImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<RestaurantError, List<Customer>> getAll() {
        Either<RestaurantError, List<Customer>> either;
        List<Customer> customers;
        entityManager = jpaUtil.getEntityManager();

        try{
            customers = entityManager.createQuery(HqlQueries.FROM_CUSTOMER, Customer.class).getResultList();
            either = Either.right(customers);

        } catch (PersistenceException e){
            either = Either.left(new RestaurantError(0,e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Customer> get(int id) {
        Either<RestaurantError, Customer> either;
        entityManager = jpaUtil.getEntityManager();

        try{
            Customer customer = entityManager.find(Customer.class, id);
            either = Either.right(customer);

        } catch (PersistenceException e) {
            either = Either.left(new RestaurantError(0,e.getMessage()));
        } finally {
            entityManager.close();
        }
        return either;
    }

    @Override
    public Either<RestaurantError, Integer> add(Customer customer) {
        Either<RestaurantError, Integer> either;

        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try{
            entityTransaction.begin();

            Credentials credentials = customer.getCredentials();
            entityManager.persist(credentials);
            customer.setIdcustomer(credentials.getIdcredentials());

            entityManager.persist(customer);
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
    public Either<RestaurantError, Integer> update(Customer customer) {
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try{
            entityTransaction.begin();
            entityManager.merge(customer);
            entityTransaction.commit();
            either = Either.right(1);

        } catch (PersistenceException e){
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0, e.getMessage()));
        } finally {
            entityManager.close();
        }
        return either;
    }

    @Override
    public Either<RestaurantError, Integer> delete(Customer costumer, boolean confirm) {
        Either<RestaurantError, Integer> either;
        if (!confirm){
            either = deleteWithoutOrders(costumer);
        } else {
            either = deleteWithOrders(costumer);
        }
        return either;
    }
    private Either<RestaurantError, Integer> deleteWithoutOrders(Customer customer){
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.remove(entityManager.merge(customer));
            entityTransaction.commit();
            either = Either.right(1);
        } catch (PersistenceException e){
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0, e.getMessage()));
        } finally {
            entityManager.close();
        }
        return either;
    }
    private Either<RestaurantError, Integer> deleteWithOrders(Customer customer){
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            //Order items
            entityManager.createNamedQuery(HqlQueries.HQL_DELETE_ORDERITEMS_BY_CUSTOMER)
                    .setParameter(HqlQueries.IDCUSTOMER, customer.getIdcustomer())
                    .executeUpdate();

            //Orders
            entityManager.createNamedQuery(HqlQueries.HQL_DELETE_FROM_ORDERS_BY_CUSTOMER)
                    .setParameter(HqlQueries.IDCUSTOMER, customer.getIdcustomer())
                    .executeUpdate();

            //Customer
            entityManager.remove(entityManager.merge(customer));
            entityTransaction.commit();
            either = Either.right(1);
        } catch (PersistenceException e){
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0, e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }
}
