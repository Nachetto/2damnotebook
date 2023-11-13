package service;

import dao.impl.CustomerDAOImpl;
import dao.impl.OrderDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Customer;
import model.Order;

import java.util.List;

public class OrderService {
    private final OrderDAOImpl dao;
    private final CustomerDAOImpl serviceCustomer;
    @Inject
    public OrderService(OrderDAOImpl dao, CustomerDAOImpl serviceCustomer) {
        this.dao = dao;
        this.serviceCustomer = serviceCustomer;
    }

    public Either<String, List<Order>> getAll() {
        return dao.getAll();
    }

    public Either<String, Order> get(int id) {
        return dao.get(id);
    }

    public int save(Order o) {
        return dao.save(o);
    }

    public int modify(Order o, Order o2) {
        return dao.modify(o, o2);
    }

    public int delete(Order o) {
        return dao.delete(o);
    }

    public Either<String, List<Order>> getOrdersByUsername(String username) {
        int customerIdResult = serviceCustomer.findIdFromUsername(username);

        Either<String, List<Order>> allOrdersResult = getAll();

        if (allOrdersResult.isLeft()) {
            return Either.left(allOrdersResult.getLeft());
        }

        List<Order> filteredOrders = allOrdersResult
                .get()
                .stream()
                .filter(order -> order.getCustomerid() == customerIdResult)
                .toList();

        return Either.right(filteredOrders);
    }


    public int delete(Customer c) {
        for (Order o : getAll().get()) {
            if (o.getCustomerid() == c.getId())
                return dao.delete(o);
        }
        return -1;
    }

    public int getLastOrderId() {
        int result = 0;
        for (Order o : getAll().get()) {
            if (o.getOrderid() < 0) {
                result = o.getOrderid();
            }
        }
        return result;
    }
}
