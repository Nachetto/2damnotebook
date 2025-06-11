package ui.screens.customers.delete;

import common.Constants;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Order;
import service.CustomerService;
import service.OrderService;
import ui.screens.common.BaseScreenController;
import ui.screens.customers.common.CustomerCommon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerDeleteController extends BaseScreenController {

    @Inject
    private CustomerCommon common;
    @FXML
    private TableView<Customer> customerlist;
    @FXML
    private TableColumn<Customer, Integer> customerphone;
    @FXML
    private TableColumn<Customer, String> customername;
    @FXML
    private TableColumn<Customer, String> customersurname;
    @FXML
    private TableColumn<Customer, String> customeremail;
    @FXML
    private TableColumn<Customer, LocalDate> customerbirthdate;
    @FXML
    private TableColumn<Customer, Integer> customerid;

    @FXML
    private TableView<Order> orderlist;
    @FXML
    private TableColumn<Order, Integer> orderid;
    @FXML
    private TableColumn<Order, Integer> tableid;
    @FXML
    private TableColumn<Order, Integer> ordercustomerid;
    @FXML
    private TableColumn<Order, LocalDateTime> orderdate;
    private Customer c;

    @Inject
    private CustomerService cservice;
    @Inject
    private OrderService oservice;

    public void initialize() {
        common.initializeCustomerTable(customerid, customerphone, customerbirthdate, customeremail, customersurname, customername);

        orderid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        tableid.setCellValueFactory(new PropertyValueFactory<>("tableid"));
        ordercustomerid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        orderdate.setCellValueFactory(new PropertyValueFactory<>("orderdate"));
    }

    public void selectedUser() {
        c = customerlist.getSelectionModel().getSelectedItem();
        orderlist.getItems().clear();
        List<Order>orders= oservice.getAll().get().stream().filter(order -> order.getCustomerid()==c.getId()).toList();
        orderlist.getItems().addAll(orders);
    }

    @Override
    public void principalCargado() {
        customerlist.getItems().addAll(cservice.getAll().get());
    }

    public void deleteUser() {
        if (cservice.hasAnyOrders(c)) {
            if (getPrincipalController().showConfirmationAlert(Constants.CONFIRMUSERDELETION)) {
                if (cservice.delete(c) != 1) {
                    getPrincipalController().showAlertError(Constants.CUSTOMERNOTDELETED);
                } else {
                    oservice.delete(c);
                    customerlist.getItems().clear();
                    principalCargado();
                    getPrincipalController().showAlertInfo(Constants.CUSTOMERDELETED);
                }
            }
        } else {
            if (cservice.delete(c) != 1) {
                getPrincipalController().showAlertError(Constants.CUSTOMERNOTDELETED);
            } else {
                customerlist.getItems().clear();
                principalCargado();
                getPrincipalController().showAlertInfo(Constants.CUSTOMERDELETED);
            }
        }
    }
}
