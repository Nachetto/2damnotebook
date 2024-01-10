package service;

import dao.OrderItemDAO;
import dao.impl.OrderItemDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.OrderItem;

import java.util.List;

public class OrderItemService {
    @Inject
    private OrderItemDAOImpl dao;

    public Either<String, List<OrderItem>> getAll() {
        return dao.getAll();
    }

    public Either<String, OrderItem> get(int id) {
        return dao.get(id);
    }

    public int save(OrderItem o) {
        return dao.save(o);
    }

    public int modify(OrderItem o) {
        return dao.modify(o);
    }

    public int delete(OrderItem o) {
        return dao.delete(o);
    }
}
