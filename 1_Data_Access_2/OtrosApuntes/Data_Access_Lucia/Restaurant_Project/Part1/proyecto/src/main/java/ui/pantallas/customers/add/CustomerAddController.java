package ui.pantallas.customers.add;

import common.Constants;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Credential;
import model.Customer;
import service.CustomerService;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.customers.common.CustomerCommon;

import java.time.LocalDate;

public class CustomerAddController extends BasePantallaController {

    @FXML
    private TextField entercustomername;
    @FXML
    private TextField entercustomersurname;
    @FXML
    private TextField entercustomeremail;
    @FXML
    private TextField entercustomerid;
    @FXML
    private TextField entercustomerphone;
    @FXML
    private DatePicker entercustomerbirthdate;
    @FXML
    private Button enterall;
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

    public void addNewCustomer(ActionEvent actionEvent) {
        int customerId = Integer.parseInt(entercustomerid.getText());
        int customerPhone = Integer.parseInt(entercustomerphone.getText());
        if (service.getAll().get().add(new Customer(customerId, customerPhone, entercustomername.getText(), entercustomersurname.getText(), entercustomeremail.getText(), new Credential("null", "null"), entercustomerbirthdate.getValue())))
            getPrincipalController().showAlertInfo(Constants.USERADDED);
        else
            getPrincipalController().showAlertError(Constants.USERRNOTADDED);
        principalCargado();
    }


    @Override
    public void principalCargado() {
        customerlist.getItems().addAll(service.getAll().get());
    }
}
