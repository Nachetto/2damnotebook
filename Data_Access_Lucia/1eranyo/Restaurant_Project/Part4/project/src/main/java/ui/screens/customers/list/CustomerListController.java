package ui.screens.customers.list;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Customer;
import service.CustomerService;
import ui.screens.common.BaseScreenController;
import ui.screens.customers.common.CustomerCommon;

import java.time.LocalDate;

public class CustomerListController extends BaseScreenController {
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

    @Inject
    private CustomerService service;

    public void initialize() {
        common.initializeCustomerTable(customerid, customerphone, customerbirthdate, customeremail, customersurname, customername);
    }

    @Override
    public void principalCargado() {
        customerlist.getItems().addAll(service.getAll().get());
    }
}
