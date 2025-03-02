package dao.staticlist.impl;

import common.RestaurantError;
import common.UtilitiesCommon;
import dao.staticlist.RestaurantOrderDAOStatic;
import io.vavr.control.Either;
import model.RestaurantOrder;

import java.util.List;

public class RestaurantOrderDAOImplStatic implements RestaurantOrderDAOStatic {

    private final List<RestaurantOrder> orders;

    public RestaurantOrderDAOImplStatic() {
        orders = List.of(
                //new RestaurantOrder(1, 4, 2, "2023-09-14 08:27:28"),
                //new RestaurantOrder(2, 2, 1, "2023-09-14 08:28:00"),
//                new RestaurantOrder(3, 1, 3, "2023-09-16 11:10:37"),
//                new RestaurantOrder(4, 3, 6, "2023-09-16 12:35:37"),
//                new RestaurantOrder(5, 1, 1, "2023-09-16 12:35:37"),
//                new RestaurantOrder(6, 2, 4, "2023-09-16 12:35:37"),
//                new RestaurantOrder(7, 5, 5, "2023-09-16 12:35:37"),
//                new RestaurantOrder(8, 6, 2, "2023-09-16 12:35:37"),
//                new RestaurantOrder(9, 6, 4, "2023-09-16 12:35:37"),
//                new RestaurantOrder(10, 2, 5, "2023-09-16 12:35:37")
        );
    }

    @Override
    public Either<RestaurantError, List<RestaurantOrder>> getAll() {
        if (orders == null) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.EMPTY));
        } else {
            return Either.right(orders);
        }
    }

    @Override
    public Either<RestaurantError, RestaurantOrder> get(int id) {
        if (id == 0) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.GETERROR));
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<RestaurantError, Integer> add(RestaurantOrder restaurantOrder) {
        if (restaurantOrder.getId() == 0) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.ADDERROR));
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<RestaurantError, Integer> update(RestaurantOrder restaurantOrder) {
        if (restaurantOrder.getId() == 0) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.UPDATERROR));
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<RestaurantError, Integer> delete(RestaurantOrder restaurantOrder) {
        if (restaurantOrder.getId() == 0) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.DELETERROR));
        } else {
            return Either.right(null);
        }
    }
}
