package dao;

import common.RestaurantError;
import io.vavr.control.Either;
import model.Credentials;

import java.util.List;

public interface CredentialsDAO {
    Either<RestaurantError, List<Credentials>> getAll();

    Either<RestaurantError, Credentials> get(int id);

    Either<RestaurantError, Integer> add(Credentials credentials);

    Either<RestaurantError, Integer> update(Credentials credentials);

    Either<RestaurantError, Integer> delete(Credentials credentials);
}
