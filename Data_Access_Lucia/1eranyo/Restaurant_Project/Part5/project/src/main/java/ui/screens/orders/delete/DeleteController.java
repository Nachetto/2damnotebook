package ui.screens.orders.delete;

import common.Constants;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.OrderItem;
import service.OrderItemService;
import service.OrderService;
import ui.screens.common.BaseScreenController;
import ui.screens.orders.common.OrderCommon;

import java.time.LocalDateTime;
import java.util.List;

public class DeleteController extends BaseScreenController {
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
    @FXML
    private TableColumn<Order, Integer> orderid;
    @FXML
    private TableColumn<Order, Integer> tableid;
    @FXML
    private TableColumn<Order, Integer> customerid;
    @FXML
    private TableColumn<Order, LocalDateTime> orderdate;
    @Inject
    private OrderCommon common;
    @FXML
    private TableView<Order> orderlist;
    @Inject
    private OrderService service;
    @Inject
    private OrderItemService service2;

    public void initialize() {
        common.initializeCustomerTable(orderid, tableid, customerid, orderdate);
        order_item_id.setCellValueFactory(new PropertyValueFactory<>("order_item_id"));
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        menu_item_id.setCellValueFactory(new PropertyValueFactory<>("menu_item_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @Override
    public void principalCargado() {
        Either<String, List<Order>> ordersResult = service.getAll();
        if (ordersResult.isLeft()) {
            getPrincipalController().showAlertError(ordersResult.getLeft());
        }
        else
            orderlist.getItems().addAll(ordersResult.get());
    }

    public void deleteOrder() {
        switch (service.delete(selectedOrder())) {
            case 1 -> getPrincipalController().showAlertInfo(Constants.ORDERDELETED);
            case -1 -> getPrincipalController().showAlertError(Constants.ORDERNOTDELETED);
        }

    }

    @FXML
    public Order selectedOrder() {
        Either<String, OrderItem> orderItemFromSelectedOrder = service2.get(orderlist.getSelectionModel().getSelectedItem().getOrderid());
        orderitemlist.getItems().clear();
        if (!orderItemFromSelectedOrder.isLeft() && orderItemFromSelectedOrder.get() != null) {
            orderitemlist.getItems().addAll(orderItemFromSelectedOrder.get());
        }
        return orderlist.getSelectionModel().getSelectedItem();
    }
}
