package service;

import common.RestaurantError;
import dao.RestaurantTableDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.RestaurantTable;

import java.util.List;

public class RestaurantTableServices {
    private final RestaurantTableDAO restaurantTableDAO;

    @Inject
    public RestaurantTableServices(RestaurantTableDAO restaurantTableDAO) {
        this.restaurantTableDAO = restaurantTableDAO;
    }

    public Either<RestaurantError, List<RestaurantTable>> getAll() {
        return restaurantTableDAO.getAll();
    }

    public RestaurantTable get(int id) {
        return restaurantTableDAO.get(id);
    }

    public int add(RestaurantTable restaurantTable) {
        return restaurantTableDAO.add(restaurantTable);
    }

    public int update(RestaurantTable restaurantTable) {
        return restaurantTableDAO.update(restaurantTable);
    }

    public int delete(RestaurantTable restaurantTable) {
        return restaurantTableDAO.delete(restaurantTable);
    }
}
