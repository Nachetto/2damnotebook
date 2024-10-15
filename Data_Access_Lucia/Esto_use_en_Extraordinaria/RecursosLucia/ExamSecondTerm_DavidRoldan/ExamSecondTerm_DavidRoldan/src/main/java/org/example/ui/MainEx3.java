package org.example.ui;

import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.model.errors.GymError;
import org.example.services.SubscriptionService;
import org.example.services.impl.SubscriptionsServiceMongo;

public class MainEx3 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        SubscriptionsServiceMongo subscriptionService = container.select(SubscriptionsServiceMongo.class).get();
        Either<GymError, Integer> either = subscriptionService.add("Sarah", "Personal Training", "Yoga Classes");

        if (either.isRight()) {
            System.out.println("Subscriptions added");
        } else if (either.isLeft()) {
            System.out.println(either.getLeft().getMessage());
        }
    }
}