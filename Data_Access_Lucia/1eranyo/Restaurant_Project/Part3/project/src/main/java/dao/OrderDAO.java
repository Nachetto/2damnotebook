package dao;

import io.vavr.control.Either;
import model.Order;

import java.util.List;

public interface OrderDAO {
    Either<String, List<Order>> getAll();

    Either<String, Order> get(int id);

    int save(Order o);

    int modify(Order o);

    int delete(Order o);
}
