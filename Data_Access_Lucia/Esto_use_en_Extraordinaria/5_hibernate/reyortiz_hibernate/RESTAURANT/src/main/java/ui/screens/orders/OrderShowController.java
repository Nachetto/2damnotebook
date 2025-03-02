package ui.screens.orders;

import common.RestaurantError;
import model.Customer;
import ui.screens.common.UtilitiesScreens;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import model.MenuItem;
import model.OrderItem;
import model.RestaurantOrder;
import service.CustomerServices;
import service.OrderItemServices;
import service.RestaurantOrderServices;
import ui.screens.common.BaseScreenController;

import java.util.List;
import java.util.Objects;

@Log4j2
public class OrderShowController extends BaseScreenController {

    //order list
    @FXML
    private TableView<RestaurantOrder> showOrderTable;
    @FXML
    private TableColumn<RestaurantOrder, Integer> orderID;
    @FXML
    private TableColumn<RestaurantOrder, Integer> orderTableNumber;
    @FXML
    private TableColumn<RestaurantOrder, String> orderIdCustomer;
    @FXML
    private TableColumn<RestaurantOrder, String> orderDate;
    //filter
    @FXML
    private ComboBox<String> comboFilter;
    @FXML
    private TextField enterCustomerID;
    @FXML
    private DatePicker enterDate;
    @FXML
    private Label labelCustomerID;
    @FXML
    private Label labelDate;
    //order details
    @FXML
    private TextField showCustomerName;
    @FXML
    private TableView<OrderItem> showOrderItems;
    @FXML
    private TableColumn<OrderItem, String> itemName;
    @FXML
    private TableColumn<OrderItem, Integer> quantity;
    @FXML
    private TableColumn<OrderItem, String> itemPrice;
    @FXML
    private TextField totalPrice;

    private final RestaurantOrderServices restaurantOrderServices;
    private final CustomerServices customerServices;
    private final OrderItemServices orderItemServices;

    @Inject
    public OrderShowController(RestaurantOrderServices restaurantOrderServices, CustomerServices customerServices, OrderItemServices orderItemServices) {
        this.restaurantOrderServices = restaurantOrderServices;
        this.customerServices = customerServices;
        this.orderItemServices = orderItemServices;
    }

    @Override
    public void loadMain() {
        //hide filters
        enterCustomerID.setVisible(false);
        enterDate.setVisible(false);
        labelCustomerID.setVisible(false);
        labelDate.setVisible(false);

        //set filter dropdown
        comboFilter.getItems().addAll(List.of(UtilitiesScreens.CUSTOMER, UtilitiesScreens.DATE));

        filterUser();

        //assign table values
        orderIdCustomer.setCellValueFactory(data -> {
            Customer customer = data.getValue().getCustomer();
            String id = String.valueOf(customer.getIdcustomer());
            return new ReadOnlyStringWrapper(id);
        });
        itemName.setCellValueFactory(data -> {
            MenuItem menuItem = data.getValue().getMenuItem();
            String name = menuItem.getName();
            return new ReadOnlyStringWrapper(name);
        });
        quantity.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.QUANTITY));
        itemPrice.setCellValueFactory(data -> {
            MenuItem menuItem = data.getValue().getMenuItem();
            String price = String.valueOf(menuItem.getPrice());
            return new ReadOnlyStringWrapper(price);
        });
    }

    public void showFilter() {
        if (Objects.equals(comboFilter.getSelectionModel().getSelectedItem(), UtilitiesScreens.CUSTOMER)) {
            //hide
            enterDate.setVisible(false);
            labelDate.setVisible(false);
            //show
            enterCustomerID.setVisible(true);
            labelCustomerID.setVisible(true);

        } else if (Objects.equals(comboFilter.getSelectionModel().getSelectedItem(), UtilitiesScreens.DATE)) {
            //hide
            enterCustomerID.setVisible(false);
            labelCustomerID.setVisible(false);
            //show
            enterDate.setVisible(true);
            labelDate.setVisible(true);
        }
    }

    public void filter() {
        if (Objects.equals(comboFilter.getSelectionModel().getSelectedItem(), UtilitiesScreens.CUSTOMER) && enterCustomerID.getText() != null) {
            Either<RestaurantError, List<RestaurantOrder>> orders = restaurantOrderServices.getAll(Integer.parseInt(enterCustomerID.getText()));
            if (orders.isRight()) {
                showOrderTable.getItems().clear();
                showOrderTable.getItems().addAll(orders.get());
            } else {
                getPrincipalController().showAlertError(orders.getLeft().getMessage());
            }
        }

        if (Objects.equals(comboFilter.getSelectionModel().getSelectedItem(), UtilitiesScreens.DATE) && enterDate.getValue() != null) {
            Either<RestaurantError, List<RestaurantOrder>> orders = restaurantOrderServices.getAll(enterDate.getValue());
            if (orders.isRight()) {
                showOrderTable.getItems().clear();
                showOrderTable.getItems().addAll(orders.get());
            } else {
                getPrincipalController().showAlertError(orders.getLeft().getMessage());
            }
        }
    }

    public void filterUser(){
        int login = getPrincipalController().getLoginId();
        Either<RestaurantError, List<RestaurantOrder>> orders;
        if (login < 0){
            orders = restaurantOrderServices.getAll();
        } else {
            orders = restaurantOrderServices.getAll(login);
        }
        if (orders.isRight()){
            setTable(orders.get());
        } else {
            getPrincipalController().showAlertError(orders.getLeft().getMessage());
        }

    }
    public void setTable(List<RestaurantOrder> orders){
        orderID.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.ID));
        orderTableNumber.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.TABLE_NUMBER));
        orderIdCustomer.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.ID_CUSTOMER));
        orderDate.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.DATE));

        showOrderTable.getItems().addAll(orders);
    }
    public void showOrder() {
        showCustomerName.setText(customerServices.get(showOrderTable.getSelectionModel().getSelectedItem().getCustomer().getIdcustomer()).get().getFirstName() + " " + customerServices.get(showOrderTable.getSelectionModel().getSelectedItem().getCustomer().getIdcustomer()).get().getLastName());
        Either<RestaurantError, List<OrderItem>> orderItems = orderItemServices.getAll(showOrderTable.getSelectionModel().getSelectedItem().getId());
        showOrderItems.getItems().clear();
        if (orderItems.isRight()) {
            Either<RestaurantError, Double> totalAmount = orderItemServices.totalAmount(orderItems.get());
            if (totalAmount.isRight()){
                totalPrice.setText(totalAmount.get().toString());
            } else {
                totalPrice.setText("0.0");
            }
            showOrderItems.getItems().addAll(orderItems.get());
        } else {
            showOrderItems.getItems().clear();
            totalPrice.clear();
            getPrincipalController().showAlertError(orderItems.getLeft().getMessage());
        }

    }
}
