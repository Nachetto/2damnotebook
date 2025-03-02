package dao.impl;

import common.RestaurantError;
import common.UtilitiesCommon;
import dao.CustomerDAO;
import dao.OrderItemDAO;
import dao.RestaurantOrderDAO;
import dao.common.HqlQueries;
import dao.common.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import model.OrderItem;
import model.RestaurantOrder;
import model.xml.OrderItemXML;
import model.xml.OrderXML;
import model.xml.OrdersXML;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Log4j2

public class RestaurantOrderDAOImpl implements RestaurantOrderDAO {
    private final JPAUtil jpaUtil;
    private EntityManager entityManager;

    //needed for the backup
    private final OrderItemDAO orderItemDAO;
    private final CustomerDAO customerDAO;

    @Inject
    public RestaurantOrderDAOImpl(JPAUtil jpaUtil, OrderItemDAO orderItemDAO, CustomerDAO customerDAO) {
        this.jpaUtil = jpaUtil;
        this.orderItemDAO = orderItemDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public Either<RestaurantError, List<RestaurantOrder>> getAll() {
        Either<RestaurantError, List<RestaurantOrder>> either;
        List<RestaurantOrder> orders;
        entityManager = jpaUtil.getEntityManager();

        try {
            orders = entityManager.createQuery("SELECT a FROM RestaurantOrder a JOIN FETCH a.orderItems", RestaurantOrder.class).getResultList();


//            orders = entityManager.createQuery("from RestaurantOrder", RestaurantOrder.class).getResultList();
            either = Either.right(orders);

        } catch (PersistenceException e){
            either = Either.left(new RestaurantError(0,e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, List<RestaurantOrder>> getAll(int id) {
        Either<RestaurantError, List<RestaurantOrder>> either;
        List<RestaurantOrder> orders;
        entityManager = jpaUtil.getEntityManager();

        try {
            orders = entityManager.createNamedQuery(HqlQueries.HQL_GET_ORDER_BY_CUSTOMER, RestaurantOrder.class)
                    .setParameter(HqlQueries.IDCUSTOMER, id)
                    .getResultList();
            either = Either.right(orders);

        } catch (PersistenceException e) {
            either = Either.left(new RestaurantError(0,e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, RestaurantOrder> get(int id) {
        Either<RestaurantError, RestaurantOrder> either;
        entityManager = jpaUtil.getEntityManager();

        try {
            RestaurantOrder restaurantOrder = entityManager.find(RestaurantOrder.class, id);
            either = Either.right(restaurantOrder);

        } catch (PersistenceException e) {
            either = Either.left(new RestaurantError(0,e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> add(RestaurantOrder restaurantOrder) {
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {

            entityTransaction.begin();
            entityManager.persist(restaurantOrder);

            for(OrderItem orderItem : restaurantOrder.getOrderItems()){
                orderItem.setOrder(restaurantOrder);
                entityManager.persist(orderItem);
            }
            entityTransaction.commit();

            either = Either.right(1);

        } catch (Exception e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0,e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> add(List<RestaurantOrder> orders) {
        Either<RestaurantError, Integer> either;
        Either<RestaurantError, Customer> getCustomer = customerDAO.get(orders.get(0).getCustomer().getIdcustomer());

        if (getCustomer.isRight()) {
            String path = UtilitiesCommon.SRC_MAIN_RESOURCES_BACKUPS + getCustomer.get().getFirstName() + getCustomer.get().getLastName() + UtilitiesCommon.XML;

            Either<RestaurantError, OrdersXML> ordersXML = convert(orders);

            if (ordersXML.isRight()) {
                Either<RestaurantError, Integer> write = write(ordersXML.get(), path);
                if (write.isRight()) {
                    either = Either.right(orders.size());
                } else {
                    either = Either.left(new RestaurantError(orders.size(), UtilitiesCommon.WRITERROR));
                }
            } else {
                either = Either.left(ordersXML.getLeft());
            }
        } else {
            either = Either.left(getCustomer.getLeft());
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> update(RestaurantOrder restaurantOrder) {
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.merge(restaurantOrder);
            entityTransaction.commit();

            either = Either.right(1);

        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0,e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public Either<RestaurantError, Integer> delete(RestaurantOrder restaurantOrder, boolean confirm) {
        Either<RestaurantError, Integer> either;
        entityManager = jpaUtil.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.remove(entityManager.merge(restaurantOrder));
            entityTransaction.commit();

            either = Either.right(1);

        } catch (PersistenceException e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            either = Either.left(new RestaurantError(0,e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }
    //receives the model orders and returns an OrdersXML object for saving
    private Either<RestaurantError, OrdersXML> convert(List<RestaurantOrder> orders) {
        List<OrderXML> ordersXML = new ArrayList<>();
        RestaurantError error = null;

        for (RestaurantOrder order : orders) {
            Either<RestaurantError, List<OrderItem>> orderItems = orderItemDAO.getAll(order.getId());

            if (orderItems.isRight()) {
                List<OrderItemXML> itemsXML = new ArrayList<>();

                for (OrderItem orderItem : orderItems.get()) {
                    itemsXML.add(new OrderItemXML(orderItem.getMenuItem().getName(), orderItem.getQuantity()));
                }
                OrderXML orderXML = new OrderXML(order.getId(), itemsXML);
                ordersXML.add(orderXML);
            } else {
                error = orderItems.getLeft();
            }
        }

        return error == null ? Either.right(new OrdersXML(ordersXML)) : Either.left(error);
    }

    //for the actual writing on the file
    private Either<RestaurantError, Integer> write(OrdersXML ordersXML, String path) {
        Either<RestaurantError, Integer> either;
        try {
            JAXBContext context = JAXBContext.newInstance(OrdersXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Path filePath = Paths.get(path);

            marshaller.marshal(ordersXML, Files.newOutputStream(filePath));

            either = Either.right(ordersXML.getList().size());
        } catch (JAXBException | IOException e) {
            either = Either.left(new RestaurantError(0, UtilitiesCommon.EMPTY));
            log.error(e.getMessage());
        }
        return either;
    }
}
