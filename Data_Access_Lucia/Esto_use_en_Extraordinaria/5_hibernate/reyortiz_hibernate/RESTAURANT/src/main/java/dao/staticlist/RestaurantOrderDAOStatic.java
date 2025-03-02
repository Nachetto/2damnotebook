package dao.staticlist;

import common.RestaurantError;
import io.vavr.control.Either;
import model.RestaurantOrder;

import java.util.List;

public interface RestaurantOrderDAOStatic {
    Either<RestaurantError, List<RestaurantOrder>> getAll();

    Either<RestaurantError, RestaurantOrder> get(int id);

    Either<RestaurantError, Integer> add(RestaurantOrder restaurantOrder);

    Either<RestaurantError, Integer> update(RestaurantOrder restaurantOrder);

    Either<RestaurantError, Integer> delete(RestaurantOrder restaurantOrder);
}
