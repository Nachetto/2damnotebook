package org.example.dao;

import io.vavr.control.Either;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;

import java.util.List;

public interface SubscriptionsDAO {
    Either<GymError, List<SubscriptionsEntity>> getAll(int idCliente);
    Either<GymError, SubscriptionsEntity> get(int id);
}
