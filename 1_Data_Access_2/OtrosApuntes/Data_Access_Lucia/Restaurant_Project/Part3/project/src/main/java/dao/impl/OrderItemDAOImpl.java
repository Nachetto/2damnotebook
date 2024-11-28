package dao.impl;

import common.Constants;
import common.config.Configuration;
import dao.OrderItemDAO;
import io.vavr.control.Either;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;
import model.*;

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


            jaxbUnmarshaller.setAdapter(new MenuItemAdapter());

            return (OrdersXML) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            log.error(Constants.ERRDB + e.getMessage());
            return null;
        }
    }

    @Override
    public Either<String, List<OrderItem>> getAll() {
        OrdersXML listxml = readOrdersFromXML();
        if (listxml != null) {
            List<OrderItem> orderitems = new ArrayList<>();
            int orderItemId = 1;
            for (OrderXML orderXML : listxml.getOrderListXML()) {
                int orderId = orderXML.getId();
                List<OrderItemXML> orderItems = orderXML.getOrderItems().getOrderItem();

                if (orderItems != null) { // Comprobar si orderItems es nulo
                    for (OrderItemXML orderItemXML : orderItems) {
                        int menuItemId;
                        try {
                            menuItemId = orderItemXML.getMenuItem().getId();
                        } catch (NumberFormatException e) {
                            menuItemId = -1;
                        }
                        int quantity = orderItemXML.getQuantity();
                        orderitems.add(new OrderItem(orderItemId, orderId, menuItemId, quantity));
                        orderItemId++;
                    }
                }
            }
            return Either.right(orderitems);
        } else {
            return Either.left(Constants.XMLLOADERROR);
        }
    }


    @Override
    public Either<String, OrderItem> get(int id) {
        List<OrderItem> orderItems = getAll().get();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrder_item_id() == id) {
                return Either.right(orderItem);
            }
        }
        return Either.left(Constants.ORDERNOTFOUND + " Requested ID: " + id);
    }

    @Override
    public int save(OrderItem o) {
        try {
            List<OrderItem> orderItems = getAll().get();

            int newId = generateNewId(orderItems);
            o.setOrder_item_id(newId);

            orderItems.add(o);

            saveOrderItemsToXml(orderItems);

            return newId;
        } catch (Exception e) {
            log.error(Constants.ERRORSAVE + e.getMessage());
            return -1;
        }
    }

    @Override
    public int modify(OrderItem o) {
        try {
            List<OrderItem> orderItems = getAll().get();

            for (OrderItem orderItem : orderItems) {
                if (orderItem.getOrder_item_id() == o.getOrder_item_id()) {
                    orderItem.setOrder_id(o.getOrder_id());
                    orderItem.setMenu_item_id(o.getMenu_item_id());
                    orderItem.setQuantity(o.getQuantity());
                    break;
                }
            }
            saveOrderItemsToXml(orderItems);

            return o.getOrder_item_id();
        } catch (Exception e) {
            log.error(Constants.ERRORMODIFY + e.getMessage());
            return -1;
        }
    }

    @Override
    public int delete(OrderItem o) {
        try {
            List<OrderItem> orderItems = getAll().get();

            orderItems.removeIf(orderItem -> orderItem.getOrder_item_id() == o.getOrder_item_id());

            saveOrderItemsToXml(orderItems);

            return o.getOrder_item_id();
        } catch (Exception e) {
            log.error(Constants.ERRORDELETE + e.getMessage());
            return -1;
        }
    }

    private int generateNewId(List<OrderItem> orderItems) {
        int maxId = 0;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrder_item_id() > maxId) {
                maxId = orderItem.getOrder_item_id();
            }
        }

        return maxId + 1;
    }

    private void saveOrderItemsToXml(List<OrderItem> orderItems) {
        try {
            OrdersXML ordersXML = readOrdersFromXML();

            if (ordersXML == null) {
                ordersXML = new OrdersXML();
            }

            for (OrderItem orderItem : orderItems) {
                OrdersXML finalOrdersXML = ordersXML;
                OrderXML orderXML = ordersXML.getOrderListXML().stream()
                        .filter(order -> order.getId() == orderItem.getOrder_id())
                        .findFirst()
                        .orElseGet(() -> {
                            OrderXML newOrder = new OrderXML();
                            newOrder.setId(orderItem.getOrder_id());
                            finalOrdersXML.getOrderListXML().add(newOrder);
                            return newOrder;
                        });

                OrderItemXML orderItemXML = new OrderItemXML();
                if (orderItemXML.getMenuItem() != null) {
                    MenuItemXML menuItemXML = new MenuItemXML();
                    menuItemXML.setId(orderItemXML.getMenuItem().getId());
                    orderItemXML.setMenuItem(menuItemXML);
                } else {
                    log.error("MenuItemXML object in OrderItem is null for orderItem with ID:  " + orderItem.getOrder_id());
                }

                if (orderItemXML.getQuantity() != 0) {
                    orderItemXML.setQuantity(orderItem.getQuantity());
                } else {
                    log.error("Quantity in OrderItem is null for orderItem with ID: " + orderItem.getOrder_id());
                }

                orderXML.getOrderItems().getOrderItem().add(orderItemXML);
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(OrdersXML.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Path xmlFilePath = Paths.get(Configuration.getInstance().getOrderItemsDataFile());
            File file = xmlFilePath.toFile();
            jaxbMarshaller.marshal(ordersXML, file);
        } catch (Exception e) {
            log.error("Error saving OrderItem elements to XML file: " + e.getMessage());
        }
    }




}
