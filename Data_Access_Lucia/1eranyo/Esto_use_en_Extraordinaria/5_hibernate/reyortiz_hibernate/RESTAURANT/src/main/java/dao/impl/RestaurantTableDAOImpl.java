package dao.impl;

import common.RestaurantError;
import dao.RestaurantTableDAO;
import dao.common.JPAUtil;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.Data;
import model.Customer;
import model.RestaurantTable;

import java.util.List;

@Data
public class RestaurantTableDAOImpl implements RestaurantTableDAO {
    private final JPAUtil jpaUtil;
    private EntityManager entityManager;

    @Inject
    public RestaurantTableDAOImpl(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Either<RestaurantError, List<RestaurantTable>> getAll() {
        Either<RestaurantError, List<RestaurantTable>> either;
        List<RestaurantTable> restaurantTables;
        entityManager = jpaUtil.getEntityManager();

        try{
            restaurantTables = entityManager.createQuery("from RestaurantTable", RestaurantTable.class).getResultList();
            either = Either.right(restaurantTables);

        } catch (PersistenceException e){
            either = Either.left(new RestaurantError(0,e.getMessage()));
        } finally {
            entityManager.close();
        }

        return either;
    }

    @Override
    public RestaurantTable get(int id) {
        return null;
    }

    @Override
    public int add(RestaurantTable restaurantTable) {
        return 0;
    }

    @Override
    public int update(RestaurantTable restaurantTable) {
        return 0;
    }

    @Override
    public int delete(RestaurantTable restaurantTable) {
        return 0;
    }
}
