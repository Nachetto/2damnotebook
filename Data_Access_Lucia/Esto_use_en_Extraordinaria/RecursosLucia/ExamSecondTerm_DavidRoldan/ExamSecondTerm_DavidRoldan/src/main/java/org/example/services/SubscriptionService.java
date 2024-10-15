package org.example.services;

import io.vavr.control.Either;
import org.example.model.errors.GymError;

public interface SubscriptionService {
    Either<GymError, Integer> add(String nameClient, String service1, String service2);

}
