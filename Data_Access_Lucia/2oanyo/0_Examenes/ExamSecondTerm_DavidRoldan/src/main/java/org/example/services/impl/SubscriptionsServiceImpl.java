package org.example.services.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.impl.ClientDaoImpl;
import org.example.dao.impl.ServicesDaoImpl;
import org.example.dao.impl.SubscriptionsDaoImpl;
import org.example.dao.impl.SubscriptionsServicesDaoImpl;
import org.example.model.ClientsEntity;
import org.example.model.ServicesEntity;
import org.example.model.SubscriptionServicesEntity;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;
import org.example.services.SubscriptionService;

public class SubscriptionsServiceImpl implements SubscriptionService {
    private final ClientDaoImpl clientDao;
    private final SubscriptionsDaoImpl subscriptionsDAO;
    private final ServicesDaoImpl servicesDAO;
    private final SubscriptionsServicesDaoImpl subscriptionsServiceDAO;

    @Inject
    public SubscriptionsServiceImpl(ClientDaoImpl clientDao, SubscriptionsDaoImpl subscriptionsDAO, ServicesDaoImpl servicesDAO, SubscriptionsServicesDaoImpl subscriptionsServiceDAO) {
        this.clientDao = clientDao;
        this.subscriptionsDAO = subscriptionsDAO;
        this.servicesDAO = servicesDAO;
        this.subscriptionsServiceDAO = subscriptionsServiceDAO;
    }

    @Override
    public Either<GymError, Integer> add(String nameClient, String service1, String service2) {
        Either<GymError, ClientsEntity> clienteither = clientDao.get(nameClient);
        ClientsEntity client = new ClientsEntity();
        if (clienteither.isRight()){
            client = clienteither.get();
        } else {
            return Either.left(clienteither.getLeft());
        }
        Either<GymError, SubscriptionsEntity> subscriptionsEntityEither = subscriptionsDAO.get(client.getId());
        SubscriptionsEntity subscription = new SubscriptionsEntity();
        if (subscriptionsEntityEither.isRight()){
            subscription = subscriptionsEntityEither.get();
        } else {
            return Either.left(subscriptionsEntityEither.getLeft());
        }

        ServicesEntity firstService = servicesDAO.get(service1).get();
        ServicesEntity secondService = servicesDAO.get(service2).get();

        subscriptionsServiceDAO.save(new SubscriptionServicesEntity(0, subscription, firstService));
        subscriptionsServiceDAO.save(new SubscriptionServicesEntity(0, subscription, secondService));

        int price = firstService.getPrice()+secondService.getPrice();

        Either<GymError, ClientsEntity> clientEither = clientDao.update(nameClient, price);
        if (clientEither.isRight()){
            return Either.right(1);
        } else {
            return Either.left(clientEither.getLeft());
        }
    }
}
