package service;

import dao.OrderDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Order;

import java.util.List;

public class OrderService {
    @Inject
    private OrderDAO dao;

    public Either<String, List<Order>> getAll() {
        return dao.getAll();
    }

    public Either<String, List<Order>> get(int id) {
        return dao.get(id);
    }

    public int save(Order o) {
        return dao.save(o);
    }

    public int modify(Order o) {
        return dao.modify(o);
    }

    public int delete(Order o) {
        return dao.delete(o);
    }

}
