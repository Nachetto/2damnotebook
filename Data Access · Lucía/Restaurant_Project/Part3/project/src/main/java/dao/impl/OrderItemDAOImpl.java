package dao.impl;

import common.config.Configuration;
import dao.OrderItemDAO;
import io.vavr.control.Either;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;
import model.OrderItem;
import model.OrderItemXML;
import model.OrderXML;
import model.OrdersXML;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class OrderItemDAOImpl implements OrderItemDAO {

    public OrdersXML readOrdersFromXML() {
        try {
            Path xmlFilePath = Paths.get(Configuration.getInstance().getOrderItemsDataFile());
            File file = xmlFilePath.toFile();

            JAXBContext jaxbContext = JAXBContext.newInstance(OrdersXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Registramos un adaptador para deserializar correctamente los elementos menuItem
            jaxbUnmarshaller.setAdapter(new MenuItemAdapter());

            return (OrdersXML) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            log.error("There was an error while reading the database: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Either<String, List<OrderItem>> getAll() {
        OrdersXML listxml = readOrdersFromXML();
        if (listxml != null) {
            List<OrderItem> orderitems = new ArrayList<>();
            int orderItemId = 1; // Inicializamos el order_item_id
            for (OrderXML orderXML : listxml.getOrderListXML()) {
                int orderId = orderXML.getId();
                List<OrderItemXML> orderItems = orderXML.getOrderItems();

                if (orderItems != null) { // Comprobar si orderItems es nulo
                    for (OrderItemXML orderItemXML : orderItems) {
                        int menuItemId;
                        try {
                            menuItemId = Integer.parseInt(orderItemXML.getMenuItem());
                        } catch (NumberFormatException e) {
                            menuItemId = -1; // Si no se especifica, lo asignamos como -1
                        }
                        int quantity = orderItemXML.getQuantity();
                        orderitems.add(new OrderItem(orderItemId, orderId, menuItemId, quantity));
                        orderItemId++; // Incrementamos el order_item_id
                    }
                }
            }
            return Either.right(orderitems);
        } else {
            return Either.left("There was an error while trying to load the XML elements");
        }
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
