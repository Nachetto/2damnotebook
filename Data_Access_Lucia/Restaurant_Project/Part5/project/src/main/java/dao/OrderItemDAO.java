package dao;

import io.vavr.control.Either;
import model.OrderItem;

import java.util.List;

public interface OrderItemDAO {

    Either<String, List<OrderItem>> getAll();

    Either<String, OrderItem> get(int id);

    int save(OrderItem o);

    int modify(OrderItem o);

    int delete(OrderItem o);
}
