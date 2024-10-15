package ui.screens.orders;

import common.RestaurantError;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import model.MenuItem;
import service.CustomerServices;
import service.MenuItemServices;
import service.RestaurantOrderServices;
import service.RestaurantTableServices;
import ui.screens.common.BaseScreenController;
import ui.screens.common.UtilitiesScreens;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderAddController extends BaseScreenController {
    @FXML
    private ComboBox<Integer> comboRestaurantTable;
    @FXML
    private ComboBox<String> comboCustomer;
    @FXML
    private ComboBox<String> comboMenuItem;
    @FXML
    private ComboBox<Integer> enterQuantity;
    @FXML
    private TableView<OrderItem> orderItemTable;
    @FXML
    private TableColumn<OrderItem, String> orderItemName;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemQuantity;
    @FXML
    private TableColumn<OrderItem, String> orderItemPrice;

    private final RestaurantOrderServices restaurantOrderServices;
    private final CustomerServices customerServices;
    private final MenuItemServices menuItemServices;
    private final RestaurantTableServices restaurantTableServices;


    @Inject
    public OrderAddController(RestaurantOrderServices restaurantOrderServices, CustomerServices customerServices, MenuItemServices menuItemServices, RestaurantTableServices restaurantTableServices) {
        this.restaurantOrderServices = restaurantOrderServices;
        this.customerServices = customerServices;
        this.menuItemServices = menuItemServices;
        this.restaurantTableServices = restaurantTableServices;
    }

    @Override
    public void loadMain() {
        //tables
        loadTableNumbers();
        loadCustomers();
        loadMenuItems();

        enterQuantity.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //item table
        orderItemName.setCellValueFactory(data -> {
            MenuItem menuItem = data.getValue().getMenuItem();
            String name = menuItem.getName();
            return new ReadOnlyStringWrapper(name);
        });
        orderItemPrice.setCellValueFactory(data -> {
            MenuItem menuItem = data.getValue().getMenuItem();
            String price = String.valueOf(menuItem.getPrice());
            return new ReadOnlyStringWrapper(price);
        });
        orderItemQuantity.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.QUANTITY));
    }

    public void loadCustomers() {
        int login = getPrincipalController().getLoginId();
        Either<RestaurantError, Customer> customer = customerServices.get(login);
        if (customer.isRight()) {
            comboCustomer.getItems().add(customer.get().getFirstName() + " " + customer.get().getLastName());
        } else {
            getPrincipalController().showAlertError(customer.getLeft().getMessage());
        }
    }

    public void loadMenuItems() {
        Either<RestaurantError, List<MenuItem>> menuItems = menuItemServices.getAll();
        if (menuItems.isRight()) {
            List<String> list = new ArrayList<>();
            for (MenuItem menuItem : menuItems.get()) {
                list.add(menuItem.getName());
            }
            comboMenuItem.getItems().addAll(list);
        } else {
            getPrincipalController().showAlertError(menuItems.getLeft().getMessage());
        }
    }

    public void add() {
        if (comboRestaurantTable.getSelectionModel().getSelectedItem() != null && comboCustomer.getSelectionModel().getSelectedItem() != null && !orderItemTable.getItems().isEmpty()) {//make sure all fields are filled out
            List<OrderItem> orderItems = orderItemTable.getItems();

            RestaurantOrder restaurantOrder = new RestaurantOrder(comboRestaurantTable.getValue(), convertCustomer(comboCustomer.getSelectionModel().getSelectedItem()), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), orderItems);
            Either<RestaurantError, Integer> addOrder = restaurantOrderServices.add(restaurantOrder);
            if (addOrder.isLeft()) {
                getPrincipalController().showAlertError(addOrder.getLeft().getMessage());
            } else {
                getPrincipalController().showAlertInformation(UtilitiesScreens.ADDORDER);
                orderItemTable.getItems().clear();
            }

        } else {//missing fields to fill out
            getPrincipalController().showAlertError(UtilitiesScreens.ADDORDERMISSING);
        }
    }

    public void addItem() {
        if (comboMenuItem.getSelectionModel().getSelectedItem() != null && enterQuantity.getSelectionModel().getSelectedItem() != null) {
            orderItemTable.getItems().add(new OrderItem(0, null, convertMenuItem(comboMenuItem.getSelectionModel().getSelectedItem()), enterQuantity.getSelectionModel().getSelectedItem()));
        } else {
            getPrincipalController().showAlertError(UtilitiesScreens.ADDITEMMISSING);
        }
    }

    public void removeItem() {
        if (orderItemTable.getSelectionModel().getSelectedItem() != null) {
            orderItemTable.getItems().removeAll(orderItemTable.getSelectionModel().getSelectedItem());
        } else {
            getPrincipalController().showAlertError(UtilitiesScreens.REMOVEITEMMISSING);
        }
    }

    public MenuItem convertMenuItem(String name) {
        MenuItem menuItem;
        Either<RestaurantError, List<MenuItem>> menuItems = menuItemServices.getAll();
        if (menuItems.isRight()) {
            menuItem = menuItems.get().stream().filter(i -> Objects.equals(i.getName(), name)).findFirst().orElse(null);
        } else {
            menuItem = null;
        }
        return menuItem;
    }

    public Customer convertCustomer(String name) {
        Customer customer;
        Either<RestaurantError, List<Customer>> customers = customerServices.getAll();
        if (customers.isRight()) {
            customer = customers.get().stream().filter(i -> Objects.equals(i.getFirstName() + " " + i.getLastName(), name)).findFirst().orElse(null);
        } else {
            customer = null;
        }
        return customer;
    }

    public void loadTableNumbers() {
        Either<RestaurantError, List<RestaurantTable>> tables = restaurantTableServices.getAll();
        List<Integer> tableNumbers = new ArrayList<>();

        if (tables.isRight()) {
            for (RestaurantTable table : tables.get()) {
                tableNumbers.add(table.getTableNumber());
            }
            comboRestaurantTable.getItems().addAll(tableNumbers);
        } else {
            getPrincipalController().showAlertError(tables.getLeft().getMessage());
        }
    }
}
