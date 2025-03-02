package org.example.services.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.AggregationDAO;
import org.example.model.errors.GymError;
import org.example.services.AggregationService;

public class AggregationServiceImpl implements AggregationService {
    private final AggregationDAO dao;

    @Inject
    public AggregationServiceImpl(AggregationDAO dao) {
        this.dao = dao;
    }


    @Override
    public Either<GymError, String> exA() {
        return dao.exA();
    }

    @Override
    public Either<GymError, String> exB() {
        return dao.exB();
    }

    @Override
    public Either<GymError, String> exC() {
        return dao.exC();
    }
}
