package service;

import dao.OrderItemDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.OrderItem;

import java.util.List;

public class OrderItemService {
    @Inject
    private OrderItemDAO dao;

    public Either<String, List<OrderItem>> getAll() {
        return dao.getAll();
    }

    public Either<String, List<OrderItem>> get(int id) {
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
