package ui.pantallas.orders.list;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Customer;
import model.Order;
import model.OrderItem;
import service.CustomerService;
import service.OrderItemService;
import service.OrderService;
import ui.pantallas.common.BaseScreenController;
import ui.pantallas.orders.common.OrderCommon;

import java.time.LocalDateTime;
import java.util.List;

public class ListController extends BaseScreenController {
    @FXML
    private TextField customertextfield;
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
    @Inject
    private CustomerService service3;

    public void initialize() {
        common.initializeCustomerTable(orderid, tableid, customerid, orderdate);
        order_item_id.setCellValueFactory(new PropertyValueFactory<>("order_item_id"));
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        menu_item_id.setCellValueFactory(new PropertyValueFactory<>("menu_item_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @Override
    public void principalCargado() {
        if (service.getAll().isLeft()) {
            getPrincipalController().showAlertError(service.getAll().getLeft());
        } else {
            orderlist.getItems().addAll(service.getAll().get());
            customertextfield.setEditable(false);
        }
    }

    public void selectedUser() {
        orderitemlist.getItems().clear();
        Order selectedOrder = orderlist.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            List<OrderItem> selectedOrderItems = service2.getAll().get().stream().filter(orderItem -> orderItem.getOrder_id() == selectedOrder.getOrderid()).toList();
            orderitemlist.getItems().addAll(selectedOrderItems);

            Order o = orderlist.getSelectionModel().getSelectedItem();
            Either<String, Customer> result = service3.get(o.getCustomerid());

            if (result.isRight()) {
                Customer customer = result.get();
                String name = customer.getName();
                customertextfield.setText(name);
            }
        }
    }


}
