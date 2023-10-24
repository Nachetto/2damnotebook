package ui.screens.customers.update;

import common.Constants;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Credential;
import model.Customer;
import service.CustomerService;
import ui.screens.common.BaseScreenController;
import ui.screens.customers.common.CustomerCommon;

import java.time.LocalDate;

public class CustomerUpdateController extends BaseScreenController {

    @Inject
    CustomerCommon common;
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
    private CustomerService service;

    public void initialize() {
        common.initializeCustomerTable(customerid, customerphone, customerbirthdate, customeremail, customersurname, customername);
    }

    @Override
    public void principalCargado() {
        customerlist.getItems().addAll(service.getAll().get());
    }

    public void updateCustomer() {
        Customer c = customerlist.getSelectionModel().getSelectedItem();
        if (c != null) {
            Customer newc= new Customer(Integer.parseInt(entercustomerid.getText()),Integer.parseInt(entercustomerphone.getText()),entercustomername.getText(),entercustomersurname.getText(),entercustomeremail.getText(),new Credential("null","null"),entercustomerbirthdate.getValue());
            if(service.modify(c,newc)!=1)
                getPrincipalController().showAlertError(Constants.CUSTOMERNOTUPDATED);
            else
                getPrincipalController().showAlertInfo(Constants.CUSTOMERUPDATED);
        }
    }

    public void selectedUser() {
        Customer c = customerlist.getSelectionModel().getSelectedItem();
        if (c != null) {
            entercustomerid.setText(Integer.toString(c.getId()));
            entercustomername.setText(c.getName());
            entercustomersurname.setText(c.getSurname());
            entercustomeremail.setText(c.getEmail());
            entercustomerbirthdate.setValue(c.getBirthdate());
            entercustomerphone.setText(Integer.toString(c.getPhone()));
        } else getPrincipalController().showAlertError(Constants.CUSTOMERNOINFO);
    }
}
