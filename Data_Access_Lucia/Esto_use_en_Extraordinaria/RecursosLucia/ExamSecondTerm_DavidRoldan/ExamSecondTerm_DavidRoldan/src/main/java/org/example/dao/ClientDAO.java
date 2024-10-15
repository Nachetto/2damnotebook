package org.example.dao;

import io.vavr.control.Either;
import org.example.model.ClientsEntity;
import org.example.model.errors.GymError;

public interface ClientDAO {
    Either<GymError, ClientsEntity> get(String name);
    Either<GymError, ClientsEntity> get(int id);
    Either<GymError, ClientsEntity> update(String name, int price);
    Either<GymError, Integer> delete(ClientsEntity client, boolean deleteSubscription);
}
