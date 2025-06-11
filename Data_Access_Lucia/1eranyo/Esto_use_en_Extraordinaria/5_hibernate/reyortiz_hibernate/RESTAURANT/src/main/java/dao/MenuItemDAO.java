package dao;


import common.RestaurantError;
import io.vavr.control.Either;
import model.MenuItem;

import java.util.List;

public interface MenuItemDAO {
    Either<RestaurantError,List<MenuItem>> getAll();

    Either<RestaurantError,MenuItem> get(int id);

    Either<RestaurantError,Integer> add(MenuItem menuItem);

    Either<RestaurantError,Integer> update(MenuItem menuItem);

    Either<RestaurantError,Integer> delete(MenuItem menuItem);

}
