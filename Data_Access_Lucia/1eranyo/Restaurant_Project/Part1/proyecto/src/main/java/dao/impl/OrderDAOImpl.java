package dao.impl;

import dao.OrderDAO;
import io.vavr.control.Either;
import model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public Either<String, List<Order>> getAll() {
        Either<String, List<Order>> result;
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, 12, 1, LocalDateTime.now()));
        orders.add(new Order(2, 2, 2, LocalDateTime.now()));
        orders.add(new Order(3, 3, 3, LocalDateTime.now()));
        result = Either.right(orders);
        return result;
    }

    @Override
    public Either<String, List<Order>> get(int id) {
        return null;
    }

    @Override
    public int save(Order o) {
        return 0;
    }

    @Override
    public int modify(Order o) {
        return 0;
    }

    @Override
    public int delete(Order o) {
        return 0;
    }

}
