package dao.impl;

import common.Constants;
import common.config.Configuration;
import dao.MenuItemDAO;
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
public class MenuItemDAOImpl implements MenuItemDAO {

    @Override
    public Either<String, List<MenuItem>> getAll() {
        try {
            Path xmlFilePath = Paths.get(Configuration.getInstance().getOrderItemsDataFile());
            File file = xmlFilePath.toFile();

            JAXBContext jaxbContext = JAXBContext.newInstance(OrdersXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            OrdersXML ordersXML = (OrdersXML) jaxbUnmarshaller.unmarshal(file);

            if (ordersXML != null && ordersXML.getOrderListXML() != null) {
                List<MenuItem> menuItems = new ArrayList<>();
                int orderItemIdCounter = 1;

                for (OrderXML orderXML : ordersXML.getOrderListXML()) {
                    if (orderXML.getOrderItems() != null) {
                        for (OrderItemXML orderItemXML : orderXML.getOrderItems().getOrderItem()) {
                            if (orderItemXML.getMenuItem() != null) {
                                MenuItemXML menuItemXML = orderItemXML.getMenuItem();
                                MenuItem menuItem = new MenuItem();
                                menuItem.setId(orderItemIdCounter++);
                                menuItem.setName(menuItemXML.getName());
                                menuItem.setDescription(menuItemXML.getDescription());
                                menuItem.setPrice(menuItemXML.getPrice());
                                menuItems.add(menuItem);
                            }
                        }
                    }
                }
                return Either.right(menuItems);
            } else {
                return Either.left(Constants.MENUITEMSXMLREADERROR);
            }
        } catch (JAXBException e) {
            log.error(Constants.ERRDB + e.getMessage());
            return Either.left(Constants.MENUITEMSXMLREADERROR);
        }
    }




    public Either<String, MenuItem> get(int id) {
        List<MenuItem> menuItems = getAll().get();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getId() == id) {
                return Either.right(menuItem);
            }
        }
        return Either.left(Constants.IDNOTFOUND + " Requested ID: " + id);
    }

    @Override
    public int save(MenuItem m) {
        try {
            List<MenuItem> menuItems = getAll().get();
            int newId = generateNewId(menuItems);
            m.setId(newId);
            menuItems.add(m);
            saveMenuItemsToXml(menuItems);

            return newId;
        } catch (Exception e) {
            log.error(Constants.ERRORSAVE + e.getMessage());
            return -1;
        }
    }

    @Override
    public int modify(MenuItem m) {
        try {
            List<MenuItem> menuItems = getAll().get();
            for (MenuItem menuItem : menuItems) {
                if (menuItem.getId() == m.getId()) {
                    menuItem.setName(m.getName());
                    menuItem.setDescription(m.getDescription());
                    menuItem.setPrice(m.getPrice());
                    break;
                }
            }
            saveMenuItemsToXml(menuItems);

            return m.getId();
        } catch (Exception e) {
            log.error(Constants.ERRORMODIFY + e.getMessage());
            return -1;
        }
    }

    @Override
    public int delete(MenuItem m) {
        try {
            List<MenuItem> menuItems = getAll().get();
            menuItems.removeIf(menuItem -> menuItem.getId() == m.getId());
            saveMenuItemsToXml(menuItems);

            return m.getId();
        } catch (Exception e) {
            log.error(Constants.ERRORDELETE + e.getMessage());
            return -1;
        }
    }

    private int generateNewId(List<MenuItem> menuItems) {
        int maxId = 0;
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getId() > maxId) {
                maxId = menuItem.getId();
            }
        }
        return maxId + 1;
    }

    private void saveMenuItemsToXml(List<MenuItem> menuItems) {
        try {
            OrdersXML ordersXML = new OrdersXML();

            for (MenuItem menuItem : menuItems) {
                OrderXML orderXML = new OrderXML();
                OrderItemXML orderItemXML = new OrderItemXML();
                MenuItemXML menuItemXML = new MenuItemXML();

                menuItemXML.setId(menuItem.getId());
                menuItemXML.setName(menuItem.getName());
                menuItemXML.setDescription(menuItem.getDescription());
                menuItemXML.setPrice(menuItem.getPrice());

                orderItemXML.setMenuItem(menuItemXML);
                orderXML.getOrderItems().getOrderItem().add(orderItemXML);
                ordersXML.getOrderListXML().add(orderXML);
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(OrdersXML.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Path xmlFilePath = Paths.get(Configuration.getInstance().getOrderItemsDataFile());
            File file = xmlFilePath.toFile();
            jaxbMarshaller.marshal(ordersXML, file);
        } catch (Exception e) {
            log.error(Constants.ERRORSAVING + e.getMessage());
        }
    }


}
