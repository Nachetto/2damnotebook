package ui.pantallas.orders.add;

import common.Constants;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderItem;
import service.OrderItemService;
import ui.pantallas.common.BasePantallaController;

public class AddController extends BasePantallaController {
    @FXML
    private DatePicker entercustomerbirthdate;
    @FXML
    private ComboBox dropdown1;
    @FXML
    private ComboBox dropdown2;
    @FXML
    private ComboBox dropdown3;
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

    public void initialize() {
        order_item_id.setCellValueFactory(new PropertyValueFactory<>("order_item_id"));
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        menu_item_id.setCellValueFactory(new PropertyValueFactory<>("menu_item_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @Override
    public void principalCargado() {
        orderitemlist.getItems().addAll(service2.getAll().get());
    }

    public void addOrder(ActionEvent actionEvent) {
        getPrincipalController().showAlertInfo(Constants.ORDERADDED);
    }

    public void addOrderItem(ActionEvent actionEvent) {
        getPrincipalController().showAlertInfo(Constants.ORDERITEMADDED);
    }

    public void removeOrderItem(ActionEvent actionEvent) {
        getPrincipalController().showAlertInfo(Constants.ORDERITEMREMOVED);
    }
}
