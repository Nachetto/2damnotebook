package org.example.services.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.ClientDAO;
import org.example.dao.impl.SubscriptionsDaoImpl;
import org.example.dao.impl.SubscriptionsDaoMongo;
import org.example.model.ClientsEntity;
import org.example.model.SubscriptionServicesEntity;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;
import org.example.model.mongo.Client;
import org.example.model.mongo.GymSubscriptions;
import org.example.services.ClientService;

import java.util.ArrayList;
import java.util.List;

public class ClientServiceImpl implements ClientService {
    private final ClientDAO clientDAO;
    private final SubscriptionsDaoImpl subscriptionsDao;
    private final SubscriptionsDaoMongo subscriptionsDaoMongo;

    @Inject
    public ClientServiceImpl(ClientDAO clientDAO, SubscriptionsDaoImpl subscriptionsDao, SubscriptionsDaoMongo subscriptionsDaoMongo) {
        this.clientDAO = clientDAO;
        this.subscriptionsDao = subscriptionsDao;
        this.subscriptionsDaoMongo = subscriptionsDaoMongo;
    }

    @Override
    public Either<GymError, Integer> delete(int idClient, boolean deleteSubscriptions) {
        Either<GymError, ClientsEntity> clienteither = clientDAO.get(idClient);
        ClientsEntity client = new ClientsEntity();
        if (clienteither.isRight()){
            client = clienteither.get();
        } else {
            return Either.left(clienteither.getLeft());
        }
        if (deleteSubscriptions) {
            backUpSubscriptions(client);
        }
        return clientDAO.delete(client, deleteSubscriptions);
    }

    private void backUpSubscriptions(ClientsEntity client) {
        List<SubscriptionsEntity> clientSubscriptions = subscriptionsDao.getAll(client.getId()).get();
        List<GymSubscriptions> gymSubscriptions = clientSubscriptions.stream().map(this::convertToGymSubscription).toList();
        gymSubscriptions.forEach(gymSubscriptions1 -> gymSubscriptions1.setClient(new Client(client.getName(), (int) client.getBalance())));
        gymSubscriptions.forEach(subscriptionsDaoMongo::save);

    }

    private GymSubscriptions convertToGymSubscription(SubscriptionsEntity s){
        return new GymSubscriptions(
                null,
                s.getStartDate().toLocalDate(),
                s.getEndDate().toLocalDate(),
                new Client(),
                convertToService(s.getSubscriptionServicesById())

        );
    }

    private ArrayList<Integer> convertToService(List<SubscriptionServicesEntity> a){
        ArrayList<Integer> services = new ArrayList<>();
        a.forEach(subscriptionServices -> services.add(subscriptionServices.getServicesByServiceId().getId()));
        return services;
    }
}
