package ui.pantallas.orders.add;

import common.Constants;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Order;
import model.OrderItem;
import service.CustomerService;
import service.OrderItemService;
import service.OrderService;
import ui.pantallas.common.BaseScreenController;

import java.time.LocalDateTime;

public class AddController extends BaseScreenController {
    @FXML
    private Spinner<Integer> quantities;
    @FXML
    private TextField tableid;
    @FXML
    private ComboBox<Integer> dropdown1;
    @FXML
    private ComboBox<OrderItem> dropdown3;
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
    private CustomerService service1;
    @Inject
    private OrderItemService service2;
    @Inject
    private OrderService service3;

    public void initialize() {
        order_item_id.setCellValueFactory(new PropertyValueFactory<>("order_item_id"));
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        menu_item_id.setCellValueFactory(new PropertyValueFactory<>("menu_item_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantities.setValueFactory(valueFactory);
    }

    @Override
    public void principalCargado() {
        orderitemlist.getItems().addAll(service2.getAll().get());
    }

    public void addOrder() {
        switch (service3.save(new Order(service3.getLastOrderId() + 1,Integer.parseInt(tableid.getText()), dropdown1.getValue(), LocalDateTime.now()))) {
            case -1 -> getPrincipalController().showAlertError(Constants.CUSTOMERNOTADDED);
            case 1 -> getPrincipalController().showAlertInfo(Constants.ORDERADDED);
        }
    }

    public void addOrderItem() {
        getPrincipalController().showAlertInfo(Constants.ORDERITEMADDED);
    }

    public void removeOrderItem() {
        getPrincipalController().showAlertInfo(Constants.ORDERITEMREMOVED);
    }

    public void showCustomerids() {
        dropdown1.getItems().clear();
        for (Customer c : service1.getAll().get()) {
            dropdown1.getItems().add(c.getId());
        }
    }

}
