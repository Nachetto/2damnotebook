package service;

import dao.impl.CustomerDAOImpl;
import dao.impl.OrderDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Customer;
import model.Order;

import java.util.List;

public class OrderService {
    @Inject
    private OrderDAOImpl dao;
    @Inject
    private CustomerDAOImpl serviceCustomer;

    public Either<String, List<Order>> getAll() {
        return dao.getAll();
    }

    public Either<String, Order> get(int id) {
        return dao.get(id);
    }

    public int save(Order o) {
        return dao.save(o);
    }

    public int modify(Order o, Order o2) {
        return dao.modify(o, o2);
    }

    public int delete(Order o) {
        return dao.delete(o);
    }

    public Either<String, List<Order>> getOrdersByUsername(String username) {
        // Primero, obtén el ID del cliente (customer) a partir del nombre de usuario.
        int customerIdResult = serviceCustomer.findIdFromUsername(username);

        // Ahora, obtén todos los pedidos (Orders).
        Either<String, List<Order>> allOrdersResult = getAll();

        // Verifica si se pudieron obtener todos los pedidos correctamente.
        if (allOrdersResult.isLeft()) {
            // En caso de error, devuelve un Either con el mensaje de error.
            return Either.left(allOrdersResult.getLeft());
        }

        // Filtra los pedidos para obtener solo los que corresponden al cliente con el ID obtenido.
        List<Order> filteredOrders = allOrdersResult
                .get()
                .stream()
                .filter(order -> order.getCustomerid() == customerIdResult)
                .toList();

        // Devuelve un Either con la lista filtrada de pedidos.
        return Either.right(filteredOrders);
    }


    public int delete(Customer c) {
        for (Order o : getAll().get()) {
            if (o.getCustomerid() == c.getId())
                return dao.delete(o);
        }
        return -1;
    }

    public int getLastOrderId() {
        int result = 0;
        for (Order o : getAll().get()) {
            if (o.getOrderid() < 0) {
                result = o.getOrderid();
            }
        }
        return result;
    }
}
