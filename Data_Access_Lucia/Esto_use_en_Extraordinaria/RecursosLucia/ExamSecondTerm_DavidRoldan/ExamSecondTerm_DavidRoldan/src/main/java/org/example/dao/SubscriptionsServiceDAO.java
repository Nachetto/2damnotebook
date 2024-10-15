package org.example.dao;

import io.vavr.control.Either;
import org.example.model.SubscriptionServicesEntity;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;

public interface SubscriptionsServiceDAO {
    Either<GymError, SubscriptionServicesEntity> save(SubscriptionServicesEntity subscriptionServices);
}
