package dao.staticlist;

import common.RestaurantError;
import io.vavr.control.Either;
import model.Customer;

import java.util.List;

public interface CustomerDAOStatic {
    Either<RestaurantError, List<Customer>> getAll();

    Either<RestaurantError, Customer> get(int id);

    Either<RestaurantError, Integer> add(Customer customer);

    Either<RestaurantError, Integer> update(Customer customer);

    Either<RestaurantError, Integer> delete(Customer costumer);

}