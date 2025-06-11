package org.example.dao;

import io.vavr.control.Either;
import org.example.model.ServicesEntity;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;

public interface ServicesDAO {
    Either<GymError, ServicesEntity> get(String name);
}
