package org.example.services.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.ClientDAO;
import org.example.dao.ServicesDAO;
import org.example.dao.SubscriptionsDAO;
import org.example.dao.SubscriptionsServiceDAO;
import org.example.dao.impl.ServicesDaoMongo;
import org.example.dao.impl.SubscriptionsDaoMongo;
import org.example.model.ClientsEntity;
import org.example.model.ServicesEntity;
import org.example.model.SubscriptionServicesEntity;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;
import org.example.model.mongo.GymServices;
import org.example.model.mongo.GymSubscriptions;
import org.example.services.SubscriptionService;

public class SubscriptionsServiceMongo implements SubscriptionService {
    private final SubscriptionsDaoMongo subscriptionsDAO;
    private final ServicesDaoMongo servicesDAO;

    @Inject
    public SubscriptionsServiceMongo(SubscriptionsDaoMongo subscriptionsDAO, ServicesDaoMongo servicesDAO) {
        this.subscriptionsDAO = subscriptionsDAO;
        this.servicesDAO = servicesDAO;
    }

    @Override
    public Either<GymError, Integer> add(String nameClient, String service1, String service2) {
        Either<GymError, GymSubscriptions> subscriptionsEither = subscriptionsDAO.get(nameClient);
        GymSubscriptions subscription;
        if (subscriptionsEither.isRight()) {
            subscription = subscriptionsEither.get();
        } else {
            return Either.left(subscriptionsEither.getLeft());
        }

        GymServices firstService = servicesDAO.get(service1).get();
        GymServices secondService = servicesDAO.get(service2).get();

        int totalPrice = firstService.getPrice() + secondService.getPrice();
        subscription.getClient().setBalance(subscription.getClient().getBalance() - totalPrice);
        if (subscription.getClient().getBalance()<=0){
            return Either.left(new GymError("Not enought money"));
        }

        subscription.getServices().add(firstService.get_id());
        subscription.getServices().add(secondService.get_id());

        Either<GymError, Integer> updateSubscriptionsEither = subscriptionsDAO.update(subscription);
        if (updateSubscriptionsEither.isRight()) {
            return Either.right(1);
        } else {
            return Either.left(updateSubscriptionsEither.getLeft());
        }
    }


}
