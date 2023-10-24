package service;

import dao.OrderDAO;
import dao.impl.OrderDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Customer;
import model.Order;

import java.util.List;

public class OrderService {
    @Inject
    private OrderDAOImpl dao;

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
        return dao.modify(o,o2);
    }

    public int delete(Order o) {
        return dao.delete(o);
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
