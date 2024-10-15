package dao;

import common.RestaurantError;
import io.vavr.control.Either;
import model.RestaurantTable;

import java.util.List;

public interface RestaurantTableDAO {
    Either<RestaurantError, List<RestaurantTable>> getAll();

    RestaurantTable get(int id);

    int add(RestaurantTable restaurantTable);

    int update(RestaurantTable restaurantTable);

    int delete(RestaurantTable restaurantTable);
}
