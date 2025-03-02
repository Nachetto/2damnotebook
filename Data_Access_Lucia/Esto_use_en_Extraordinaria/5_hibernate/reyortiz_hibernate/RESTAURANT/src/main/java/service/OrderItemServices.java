package service;

import common.RestaurantError;
import common.UtilitiesCommon;
import dao.OrderItemDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.OrderItem;
import ui.screens.common.UtilitiesScreens;

import java.util.List;

public class OrderItemServices {
    private final OrderItemDAO orderItemDAO;

    @Inject
    public OrderItemServices(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    public Either<RestaurantError, List<OrderItem>> getAll(int orderId) {
        return orderItemDAO.getAll(orderId);
    }

    public Either<RestaurantError, Integer> add(OrderItem orderItem) {
        return orderItemDAO.add(orderItem);
    }

    public Either<RestaurantError, Integer> add(List<OrderItem> orderItems) {
        return orderItemDAO.add(orderItems);
    }

    public Either<RestaurantError, Integer> update(OrderItem orderItem) {
        return orderItemDAO.update(orderItem);
    }

    public Either<RestaurantError, Integer> delete(OrderItem orderItem) {
        return orderItemDAO.delete(orderItem);
    }
    public Either<RestaurantError, Integer> delete(List<OrderItem> orderItems) {
        return orderItemDAO.delete(orderItems);
    }

    public Either<RestaurantError, Integer> updateByOrder(List<OrderItem> orderItems) {
        Either<RestaurantError, Integer> either;
        Either<RestaurantError, Integer> checkChanges = checkChanges(orderItems);
        if (checkChanges.isRight()) {
            Either<RestaurantError, Integer> delete = delete(orderItems);
            if (delete.isRight()) {
                either = add(orderItems);
            } else {
                either = Either.left(delete.getLeft());
            }
        } else {
            either = Either.right(checkChanges.getLeft().getNumber());
        }
        return either;
    }

    public Either<RestaurantError, Integer> checkChanges(List<OrderItem> orderItems) {
        Either<RestaurantError, Integer> either;
        Either<RestaurantError, List<OrderItem>> olist = getAll(orderItems.get(0).getOrder().getId());
        if (olist.isRight() && orderItems.equals(olist.get())) {
            either = Either.left(new RestaurantError(0, UtilitiesCommon.NO_UPDATES));
        } else {
            either = Either.right(orderItems.size());
        }
        return either;
    }
    public Either<RestaurantError, Double> totalAmount(List<OrderItem> orderItems){
        double total = 0;
        for(OrderItem orderItem : orderItems){
            total += orderItem.getMenuItem().getPrice() * orderItem.getQuantity();
        }
        return total > 0 ? Either.right(total) : Either.left(new RestaurantError(0, UtilitiesScreens.TOTAL_AMOUNT_COULDN_T_BE_CALCULATED));
    }
}