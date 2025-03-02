package service;

import common.RestaurantError;
import common.UtilitiesCommon;
import dao.CredentialsDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credentials;

import java.util.List;

public class CredentialsServices {
    private final CredentialsDAO credentialsDAO;

    @Inject
    public CredentialsServices(CredentialsDAO credentialsDAO) {
        this.credentialsDAO = credentialsDAO;
    }

    public Either<RestaurantError, List<Credentials>> getAll() {
        return credentialsDAO.getAll();
    }

    public Either<RestaurantError, Credentials> get(int id) {
        return credentialsDAO.get(id);
    }

    public Either<RestaurantError, Integer> add(Credentials credentials) {
        return credentialsDAO.add(credentials);
    }

    public Either<RestaurantError, Integer> update(Credentials credentials) {
        return credentialsDAO.update(credentials);
    }

    public Either<RestaurantError, Integer> delete(Credentials credentials) {
        return credentialsDAO.delete(credentials);
    }
    public Either<RestaurantError, Credentials> login(Credentials credentials){
        Either<RestaurantError, Credentials> login;
        Either<RestaurantError,List<Credentials>> getall = credentialsDAO.getAll();
        if (getall.isRight()){
            Credentials check = getall.get().stream()
                    .filter(i -> i.getUsername().equals(credentials.getUsername()) && i.getPassword().equals(credentials.getPassword()))
                    .findFirst().orElse(null);
            if(check != null){
                login = Either.right(check);
            } else {
                login = Either.left(new RestaurantError(0, UtilitiesCommon.WRONG_CREDENTIALS));
            }
        } else {
            login = Either.left(new RestaurantError(0,getall.getLeft().getMessage()));
        }
        return login;
    }
}
