package ui.screens.orders.add;

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
import ui.screens.common.BaseScreenController;

import java.time.LocalDateTime;

public class AddController extends BaseScreenController {
    @FXML
    private Spinner<Integer> quantities;
    @FXML
    private TextField tableid;
    @FXML
    private ComboBox<Integer> dropdown1;
    @FXML
    private TableView<OrderItem> orderitemlist;
    @FXML
    private TableColumn<OrderItem, Integer> orderItemId;
    @FXML
    private TableColumn<OrderItem, Integer> orderId;
    @FXML
    private TableColumn<OrderItem, Integer> menuItemId;
    @FXML
    private TableColumn<OrderItem, Integer> quantity;
    private final CustomerService service1;
    private final OrderItemService service2;
    private final OrderService service3;
    @Inject
    public AddController(CustomerService service1, OrderItemService service2, OrderService service3) {
        this.service1 = service1;
        this.service2 = service2;
        this.service3 = service3;
    }

    public void initialize() {
        orderItemId.setCellValueFactory(new PropertyValueFactory<>("order_item_id"));
        orderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        menuItemId.setCellValueFactory(new PropertyValueFactory<>("menu_item_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantities.setValueFactory(valueFactory);
    }

    @Override
    public void principalCargado() {
        orderitemlist.getItems().addAll(service2.getAll().get());
    }

    public void addOrder() {
        int save = service3.save(new Order(service3.getLastOrderId() + 1, Integer.parseInt(tableid.getText()), dropdown1.getValue(), LocalDateTime.now()));
        if (save == -1) {
            getPrincipalController().showAlertError(Constants.CUSTOMERNOTADDED);
        } else if (save == 1) {
            getPrincipalController().showAlertInfo(Constants.ORDERADDED);
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
