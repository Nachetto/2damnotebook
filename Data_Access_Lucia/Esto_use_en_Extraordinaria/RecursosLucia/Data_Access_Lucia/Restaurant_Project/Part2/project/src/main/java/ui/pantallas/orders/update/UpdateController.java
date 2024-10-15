package ui.pantallas.orders.update;

import common.Constants;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Order;
import model.OrderItem;
import service.OrderItemService;
import service.OrderService;
import ui.pantallas.common.BaseScreenController;
import ui.pantallas.orders.common.OrderCommon;

import java.time.LocalDateTime;

public class UpdateController extends BaseScreenController {
    @FXML
    private TableColumn<Order, Integer> orderid;
    @FXML
    private TableColumn<Order, Integer> tableid;
    @FXML
    private TableColumn<Order, Integer> customerid;
    @FXML
    private TableColumn<Order, LocalDateTime> orderdate;
    @FXML
    private TableView<Order> orderlist;
    @FXML
    private DatePicker entercustomerbirthdate;
    @FXML
    private ComboBox<Integer> dropdown1;
    @FXML
    private ComboBox<Integer> dropdown2;
    @FXML
    private ComboBox<Integer> dropdown3;
    @FXML
    private TableView<OrderItem> orderitemlist;
    @FXML
    private TableColumn<OrderItem, Integer> order_item_id;
    @FXML
    private TableColumn<OrderItem, Integer> order_id;
    @FXML
    private TableColumn<OrderItem, Integer> menu_item_id;
    @FXML
    private TableColumn<OrderItem, Integer> quantity;
    @Inject
    private OrderItemService service2;
    @Inject
    private OrderCommon common;
    @Inject
    private OrderService service;

    public void initialize() {
        common.initializeCustomerTable(orderid, tableid, customerid, orderdate);
        order_item_id.setCellValueFactory(new PropertyValueFactory<>("order_item_id"));
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        menu_item_id.setCellValueFactory(new PropertyValueFactory<>("menu_item_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderitemlist.getItems().addAll(service2.getAll().get());
    }

    @Override
    public void principalCargado() {
        orderlist.getItems().addAll(service.getAll().get());
        orderitemlist.getItems().addAll(service2.getAll().get());
    }

    public void addOrder() {
        getPrincipalController().showAlertInfo(Constants.ORDERUPDATED);
    }

    public void addOrderItem() {
        getPrincipalController().showAlertInfo(Constants.ORDERITEMADDED);
    }

    public void removeOrderItem() {
        getPrincipalController().showAlertInfo(Constants.ORDERITEMREMOVED);
    }

    public void selectedUser() {//not done yet because not asked to
    }
}
