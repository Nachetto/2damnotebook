package ui.screens.orders;

import common.RestaurantError;
import model.Customer;
import ui.screens.common.UtilitiesScreens;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import model.MenuItem;
import model.OrderItem;
import model.RestaurantOrder;
import service.OrderItemServices;
import service.RestaurantOrderServices;
import ui.screens.common.BaseScreenController;

import java.util.List;

@Log4j2
public class OrderDeleteController extends BaseScreenController {
    @FXML
    private TableView<RestaurantOrder> deleteOrderTable;
    @FXML
    private TableColumn<RestaurantOrder, Integer> orderID;
    @FXML
    private TableColumn<RestaurantOrder, Integer> orderTable;
    @FXML
    private TableColumn<RestaurantOrder, String> orderCustomerID;
    @FXML
    private TableColumn<RestaurantOrder, String> orderDate;
    @FXML
    private TableView<OrderItem> orderItemTable;
    @FXML
    private TableColumn<OrderItem, String> orderItemName;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemQuantity;
    @FXML
    private TableColumn<OrderItem, String> orderItemPrice;

    private final RestaurantOrderServices restaurantOrderServices;
    private final OrderItemServices orderItemServices;

    @Inject
    public OrderDeleteController(RestaurantOrderServices restaurantOrderServices, OrderItemServices orderItemServices) {
        this.restaurantOrderServices = restaurantOrderServices;
        this.orderItemServices = orderItemServices;
    }

    @Override
    public void loadMain() {
        //order list
        Either<RestaurantError, List<RestaurantOrder>> orders = restaurantOrderServices.getAll();
        if (orders.isLeft()) {
            log.error(orders.getLeft());
            getPrincipalController().showAlertError(orders.getLeft().getMessage());
        } else {
            orderCustomerID.setCellValueFactory(data -> {
                Customer customer = data.getValue().getCustomer();
                String id = String.valueOf(customer.getIdcustomer());
                return new ReadOnlyStringWrapper(id);
            });

            orderID.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.ID));
            orderTable.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
            orderDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

            deleteOrderTable.getItems().addAll(restaurantOrderServices.getAll().getOrNull());
        }

        //order details

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
    }

    public void delete() {
        RestaurantOrder order = deleteOrderTable.getSelectionModel().getSelectedItem();

        if (order != null) {//check item selected
            Either<RestaurantError, Integer> deleteWithout = restaurantOrderServices.delete(order, false);
            if(deleteWithout.isLeft()){//delete orders without items

                if (getPrincipalController().showAlertConfirmation(UtilitiesScreens.CONFDELETEORDERS)) {//confirm deleting orders

                    Either<RestaurantError,Integer> delete = restaurantOrderServices.delete(order,true);
                    if (delete.isLeft()){
                        getPrincipalController().showAlertError(delete.getLeft().getMessage());
                    } else{
                        getPrincipalController().showAlertInformation(UtilitiesScreens.DELETEORDER);
                        orderItemTable.getItems().clear();
                        deleteOrderTable.getItems().clear();
                        loadMain();
                    }
                } else {
                    getPrincipalController().showAlertInformation(UtilitiesScreens.CANCEL);
                }
            } else {
                getPrincipalController().showAlertConfirmation(UtilitiesScreens.DELETEORDER);
                deleteOrderTable.getItems().clear();
                loadMain();
            }

        } else {
            getPrincipalController().showAlertInformation(UtilitiesScreens.SELECTORDER);
        }
    }

    public void showOrder() {
        if (deleteOrderTable.getSelectionModel().getSelectedItem() != null) {
            Either<RestaurantError, List<OrderItem>> orderItems = orderItemServices.getAll(deleteOrderTable.getSelectionModel().getSelectedItem().getId());
            orderItemTable.getItems().clear();
            if (orderItems.isRight()) {
                orderItemTable.getItems().addAll(orderItems.get());
            } else {
                getPrincipalController().showAlertError(orderItems.getLeft().getMessage());
            }
        }

    }
}
