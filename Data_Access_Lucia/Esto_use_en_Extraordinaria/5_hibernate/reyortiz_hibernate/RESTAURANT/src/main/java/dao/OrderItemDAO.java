package dao;


import common.RestaurantError;
import io.vavr.control.Either;
import model.OrderItem;


import java.util.List;

public interface OrderItemDAO {
    Either<RestaurantError, List<OrderItem>> getAll(int orderId);
    Either<RestaurantError, Integer> add(OrderItem orderItem);
    Either<RestaurantError,Integer> add(List<OrderItem> orderItems);

    Either<RestaurantError, Integer> update(OrderItem orderItem);

    Either<RestaurantError, Integer> delete(OrderItem orderItem);
    Either<RestaurantError, Integer> delete(List<OrderItem> orderItems);
}
