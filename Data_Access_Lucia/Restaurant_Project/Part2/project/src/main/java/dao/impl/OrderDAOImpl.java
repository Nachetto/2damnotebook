package dao.impl;

import common.Constants;
import common.config.Configuration;
import dao.OrderDAO;
import io.vavr.control.Either;
import model.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public Either<String, List<Order>> getAll() {
        List<Order> orders = new ArrayList<>();
        try {
            List<String> lines = readAllLines(Paths.get(Configuration.getInstance().getOrderDataFile()));
            for (String line : lines.subList(1, lines.size())) {
                orders.add(new Order(line));
            }
            return Either.right(orders);
        } catch (IOException | NumberFormatException e) {
            return Either.left(Constants.ORDERDBERROR + e.getMessage());
        }
    }

    @Override
    public Either<String, Order> get(int id) {
        return null;
    }

    @Override
    public int save(Order o) {
        try {
            Files.write(Paths.get(Configuration.getInstance().getOrderDataFile()), ('\n' + o.toStringTextFile()).getBytes(), StandardOpenOption.APPEND);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int modify(Order o) {
        return 0;
    }

    @Override
    public int delete(Order o) {
        return 0;
    }

}
