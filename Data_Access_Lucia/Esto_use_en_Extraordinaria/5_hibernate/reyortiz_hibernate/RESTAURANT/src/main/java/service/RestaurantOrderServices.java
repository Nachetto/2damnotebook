package service;

import common.RestaurantError;
import dao.RestaurantOrderDAO;


import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.RestaurantOrder;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Log4j2
public class RestaurantOrderServices {
    private final RestaurantOrderDAO restaurantOrderDAO;
    private final OrderItemServices orderItemServices;



    @Inject
    public RestaurantOrderServices(RestaurantOrderDAO restaurantOrderDAO, OrderItemServices orderItemServices) {
        this.restaurantOrderDAO = restaurantOrderDAO;
        this.orderItemServices = orderItemServices;
    }

    public Either<RestaurantError, List<RestaurantOrder>> getAll() {
        return restaurantOrderDAO.getAll();
    }


    public Either<RestaurantError, List<RestaurantOrder>> getAll(int id) {
        return restaurantOrderDAO.getAll(id);
    }

    public Either<RestaurantError, List<RestaurantOrder>> getAll(LocalDate localDate) {
        if (restaurantOrderDAO.getAll().isRight()) {
            //for some reason the equals() method doesn't work here
            return Either.right(restaurantOrderDAO.getAll().getOrNull().stream().filter(i -> i.getDate().toLocalDate().isEqual(localDate)).toList());
        } else {
            return Either.left(restaurantOrderDAO.getAll().getLeft());
        }
    }

    public Either<RestaurantError, RestaurantOrder> get(int id) {
        return restaurantOrderDAO.get(id);
    }

    public Either<RestaurantError, Integer> add(RestaurantOrder restaurantOrder) {
        return restaurantOrderDAO.add(restaurantOrder);
    }

    public Either<RestaurantError, Integer> add(List<RestaurantOrder> restaurantOrders) {
        return restaurantOrderDAO.add(restaurantOrders);
    }

    public Either<RestaurantError, Integer> update(RestaurantOrder restaurantOrder) {
        Either<RestaurantError, Integer> either;
        Either<RestaurantError, Integer> updateItems = orderItemServices.updateByOrder(restaurantOrder.getOrderItems());
        if (updateItems.isRight()) {
            either = restaurantOrderDAO.update(restaurantOrder);
        } else {
            either = Either.left(updateItems.getLeft());
        }
        return either;
    }

    public Either<RestaurantError, Integer> delete(RestaurantOrder restaurantOrder, boolean confirm) {
        return restaurantOrderDAO.delete(restaurantOrder, confirm);
    }

    public Either<RestaurantError, Integer> generateId() {
        Either<RestaurantError, Integer> getId;
        Either<RestaurantError, List<RestaurantOrder>> read = getAll();
        if (read.isRight()) {
            RestaurantOrder maxOrder = read.get().stream().max(Comparator.comparing(RestaurantOrder::getId)).orElse(null);
            if (maxOrder != null) {
                getId = Either.right(maxOrder.getId() + 1);
            } else {
                getId = Either.right(1);
            }
        } else {
            getId = Either.left(read.getLeft());
        }
        return getId;
    }
}
