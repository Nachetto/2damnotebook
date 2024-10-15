package org.example.dao;

import io.vavr.control.Either;
import org.example.model.errors.GymError;

public interface AggregationDAO {

    Either<GymError, String> exA();
    Either<GymError, String> exB();
    Either<GymError, String> exC();

}
