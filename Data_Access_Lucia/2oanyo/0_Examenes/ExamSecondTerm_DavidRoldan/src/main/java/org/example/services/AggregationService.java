package org.example.services;

import io.vavr.control.Either;
import org.example.model.errors.GymError;

public interface AggregationService {
    Either<GymError, String> exA();
    Either<GymError, String> exB();
    Either<GymError, String> exC();
}
