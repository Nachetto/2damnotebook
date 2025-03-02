package ui.screens.orders;

import common.RestaurantError;
import model.*;
import model.MenuItem;
import service.*;
import ui.screens.common.UtilitiesScreens;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import ui.screens.common.BaseScreenController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public class OrderUpdateController extends BaseScreenController {

    @FXML
    private TableView<RestaurantOrder> restaurantOrderTable;
    @FXML
    private TableColumn<RestaurantOrder, Integer> orderID;
    @FXML
    private TableColumn<RestaurantOrder, Integer> orderTable;
    @FXML
    private TableColumn<RestaurantOrder, String> orderCustomerID;
    @FXML
    private ComboBox<Integer> comboTable;
    @FXML
    private ComboBox<String> comboCustomer;
    @FXML
    private ComboBox<String> comboItem;
    @FXML
    private TableView<OrderItem> updateItemTable;
    @FXML
    private TableColumn<OrderItem, String> orderItemName;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemQuantity;
    @FXML
    private TableColumn<OrderItem, String> orderItemPrice;
    @FXML
    private Label enterDate;
    @FXML
    private ComboBox<Integer> enterQuantity;

    private final RestaurantOrderServices restaurantOrderServices;
    private final OrderItemServices orderItemServices;
    private final CustomerServices customerServices;
    private final MenuItemServices menuItemServices;
    private final RestaurantTableServices restaurantTableServices;

    @Inject
    public OrderUpdateController(RestaurantOrderServices restaurantOrderServices, OrderItemServices orderItemServices, CustomerServices customerServices, MenuItemServices menuItemServices, RestaurantTableServices restaurantTableServices) {
        this.restaurantOrderServices = restaurantOrderServices;
        this.orderItemServices = orderItemServices;
        this.customerServices = customerServices;
        this.menuItemServices = menuItemServices;
        this.restaurantTableServices = restaurantTableServices;
    }

    @Override
    public void loadMain() {

        //load orders and customers
        int login = getPrincipalController().getLoginId();
        filterCustomer(login);

        //tables
        loadTableNumbers();
        orderCustomerID.setCellValueFactory(data -> {
            Customer customer = data.getValue().getCustomer();
            String id = String.valueOf(customer.getIdcustomer());
            return new ReadOnlyStringWrapper(id);
        });

        //items
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
        orderItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        loadComboItems();
        enterQuantity.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    public void filterCustomer(int login) {
        Either<RestaurantError, List<RestaurantOrder>> orders;
        if (login < 0) {
            orders = restaurantOrderServices.getAll();
            loadCustomers();
        } else {
            orders = restaurantOrderServices.getAll(login);
            loadCustomers(login);
        }
        if (orders.isRight()) {
            setTable(orders.get());
        } else {
            log.error(orders.getLeft().getMessage());
            getPrincipalController().showAlertError(orders.getLeft().getMessage());
        }

    }

    public void setTable(List<RestaurantOrder> orders) {
        orderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderTable.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        orderCustomerID.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));

        restaurantOrderTable.getItems().addAll(orders);
    }

    public void loadCustomers() {
        Either<RestaurantError, List<Customer>> customer = customerServices.getAll();
        if (customer.isRight()) {
            loadComboCustomers(customer.get());
        } else {
            getPrincipalController().showAlertError(customer.getLeft().getMessage());
        }
    }

    public void loadCustomers(int login) {
        Either<RestaurantError, Customer> customer = customerServices.get(login);
        if (customer.isRight()) {
            comboCustomer.getItems().add(customer.get().getFirstName() + " " + customer.get().getLastName());
        } else {
            getPrincipalController().showAlertError(customer.getLeft().getMessage());
        }
    }

    public void loadComboItems() {
        Either<RestaurantError, List<MenuItem>> menuItems = menuItemServices.getAll();
        if (menuItems.isRight()) {
            List<String> list = new ArrayList<>();
            for (MenuItem menuItem : menuItems.get()) {
                list.add(menuItem.getName());
            }
            comboItem.getItems().addAll(list);
        } else {
            getPrincipalController().showAlertError(menuItems.getLeft().getMessage());
        }
    }

    public void loadComboCustomers(List<Customer> customers) {
        List<String> list = new ArrayList<>();
        for (Customer customer : customers) {
            list.add(customer.getFirstName() + " " + customer.getLastName());
        }
        comboCustomer.getItems().addAll(list);
    }

    public void showOrder() {
        enterDate.setText(restaurantOrderTable.getSelectionModel().getSelectedItem().getDate().toLocalDate().toString());
        comboTable.setValue(restaurantOrderTable.getSelectionModel().getSelectedItem().getTableNumber());

        Either<RestaurantError, Customer> customer = customerServices.get(restaurantOrderTable.getSelectionModel().getSelectedItem().getCustomer().getIdcustomer());
        if (customer.isRight()) {
            comboCustomer.setValue(customer.get().getFirstName() + " " + customer.get().getLastName());
        } else {
            getPrincipalController().showAlertError(customer.getLeft().getMessage());

        }

        Either<RestaurantError, List<OrderItem>> orderItems = orderItemServices.getAll(restaurantOrderTable.getSelectionModel().getSelectedItem().getId());
        updateItemTable.getItems().clear();
        if (orderItems.isRight()) {
            updateItemTable.getItems().addAll(orderItems.get());
        } else {
            getPrincipalController().showAlertError(orderItems.getLeft().getMessage());
        }
    }

    public void update() {
        if (comboTable.getValue() != null && comboCustomer.getSelectionModel().getSelectedItem() != null && !updateItemTable.getItems().isEmpty()) {//make sure all fields are filled out
            List<OrderItem> orderItems = updateItemTable.getItems();

            RestaurantOrder order = restaurantOrderTable.getSelectionModel().getSelectedItem();
            order.setOrderItems(orderItems);

            Either<RestaurantError, Integer> updateOrder = restaurantOrderServices.update(order);

            if (updateOrder.isLeft()) {
                getPrincipalController().showAlertError(updateOrder.getLeft().getMessage());
            } else {
                getPrincipalController().showAlertInformation(UtilitiesScreens.UPDATEORDER);
                updateItemTable.getItems().clear();
                loadMain();
            }
        } else {
            getPrincipalController().showAlertError(UtilitiesScreens.ADDCUSTOMERMISSING);
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

    public void addItem() {
        if (comboItem.getSelectionModel().getSelectedItem() != null && enterQuantity.getSelectionModel().getSelectedItem() != null) {
            updateItemTable.getItems().add(new OrderItem(0, restaurantOrderTable.getSelectionModel().getSelectedItem(), convertMenuItem(comboItem.getSelectionModel().getSelectedItem()) , enterQuantity.getSelectionModel().getSelectedItem()));
        } else {
            getPrincipalController().showAlertError(UtilitiesScreens.ADDITEMMISSING);
        }
    }

    public void removeItem() {
        if (updateItemTable.getSelectionModel().getSelectedItem() != null) {
            updateItemTable.getItems().removeAll(updateItemTable.getSelectionModel().getSelectedItem());
        } else {
            getPrincipalController().showAlertError(UtilitiesScreens.REMOVEITEMMISSING);
        }
    }
    public void loadTableNumbers() {
        Either<RestaurantError, List<RestaurantTable>> tables = restaurantTableServices.getAll();
        List<Integer> tableNumbers = new ArrayList<>();

        if (tables.isRight()) {
            for (RestaurantTable table : tables.get()) {
                tableNumbers.add(table.getTableNumber());
            }
            comboTable.getItems().addAll(tableNumbers);
        } else {
            getPrincipalController().showAlertError(tables.getLeft().getMessage());
        }
    }
}
