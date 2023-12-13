package ui.screens.orders.update;

import common.Constants;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.OrderItem;
import service.OrderItemService;
import service.OrderService;
import ui.screens.common.BaseScreenController;
import ui.screens.orders.common.OrderCommon;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateController extends BaseScreenController {
    @FXML
    private TextField labelCustomerId;
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
    private ComboBox<Integer> dropdownTableID;
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
    }

    @Override
    public void principalCargado() {

        String username = getPrincipalController().getUsername();
        Either<String, List<Order>> ordersResult;
        // Llama al método getOrdersByUsername para obtener los pedidos del usuario
        if (!username.equalsIgnoreCase("root")) {
            ordersResult = service.getOrdersByUsername(username);
        } else {
            ordersResult = service.getAll();
        }

        if (ordersResult.isLeft()) {
            getPrincipalController().showAlertError(ordersResult.getLeft());
        } else {
            orderlist.getItems().addAll(ordersResult.get());
        }
    }

    public void updateOrder() {
        //(int orderid, int tableid, int customerid, LocalDateTime orderdate)
        service.modify(orderlist.getSelectionModel().getSelectedItem(), new Order(
                orderlist.getSelectionModel().getSelectedItem().getOrderid(),
                dropdownTableID.getSelectionModel().getSelectedItem(),
                orderlist.getSelectionModel().getSelectedItem().getCustomerid(),
                entercustomerbirthdate.getValue().atStartOfDay()
        ));
        getPrincipalController().showAlertInfo(Constants.ORDERUPDATED);
    }

    public void addOrderItem() {

        getPrincipalController().showAlertInfo(Constants.ORDERITEMADDED);
    }

    public void removeOrderItem() {
        getPrincipalController().showAlertInfo(Constants.ORDERITEMREMOVED);
    }

    public void cargarTableIDS() {
        dropdownTableID.getItems().clear();
        for (Order o : orderlist.getItems()) {
            dropdownTableID.getItems().add(o.getCustomerid());
        }
    }

    @FXML
    public Order selectedOrder() {
        Either<String, OrderItem> orderItemFromSelectedOrder = service2.get(orderlist.getSelectionModel().getSelectedItem().getOrderid());
        orderitemlist.getItems().clear();
        if (!orderItemFromSelectedOrder.isLeft() && orderItemFromSelectedOrder.get() != null) {
            orderitemlist.getItems().addAll(orderItemFromSelectedOrder.get());
            entercustomerbirthdate.valueProperty().set(orderlist.getSelectionModel().getSelectedItem().getOrderdate().toLocalDate());
            labelCustomerId.setText(String.valueOf(orderlist.getSelectionModel().getSelectedItem().getCustomerid()));
        }

        return orderlist.getSelectionModel().getSelectedItem();
    }
}
