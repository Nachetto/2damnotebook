package org.example.services;

import io.vavr.control.Either;
import org.example.model.errors.GymError;

public interface ClientService {
    Either<GymError, Integer> delete(int idClient, boolean deleteSubscriptions);

}
