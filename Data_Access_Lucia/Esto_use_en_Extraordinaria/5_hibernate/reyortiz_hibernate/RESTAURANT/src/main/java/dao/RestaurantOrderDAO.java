package dao;

import common.RestaurantError;
import io.vavr.control.Either;
import model.RestaurantOrder;

import java.util.List;

public interface RestaurantOrderDAO {
    Either<RestaurantError, List<RestaurantOrder>> getAll();
    Either<RestaurantError,List<RestaurantOrder>> getAll(int id);

    Either<RestaurantError, RestaurantOrder> get(int id);

    Either<RestaurantError, Integer> add(RestaurantOrder restaurantOrder);
    Either<RestaurantError, Integer> add(List<RestaurantOrder> orders);

    Either<RestaurantError, Integer> update(RestaurantOrder restaurantOrder);

    Either<RestaurantError, Integer> delete(RestaurantOrder restaurantOrder, boolean confirm);

}
