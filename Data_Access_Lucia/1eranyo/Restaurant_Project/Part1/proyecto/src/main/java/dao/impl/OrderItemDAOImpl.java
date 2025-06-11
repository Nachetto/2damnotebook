package dao.impl;

import dao.OrderItemDAO;
import io.vavr.control.Either;
import model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {

    @Override
    public Either<String, List<OrderItem>> getAll() {
        Either<String, List<OrderItem>> result;
        List<OrderItem> orderitems = new ArrayList<>();
        orderitems.add(new OrderItem(1, 1, 1, 2));
        orderitems.add(new OrderItem(2, 2, 2, 6));
        orderitems.add(new OrderItem(3, 3, 3, 4));
        result = Either.right(orderitems);
        return result;
    }

    @Override
    public Either<String, List<OrderItem>> get(int id) {
        return null;
    }

    @Override
    public int save(OrderItem o) {
        return 0;
    }

    @Override
    public int modify(OrderItem o) {
        return 0;
    }

    @Override
    public int delete(OrderItem o) {
        return 0;
    }
}
