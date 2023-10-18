package ui.screens.customers.add;

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

public class CustomerAddController extends BaseScreenController {

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

    public void addNewCustomer() {
        String customerIdText = entercustomerid.getText();
        String customerPhoneText = entercustomerphone.getText();
        LocalDate customerBirthdate = entercustomerbirthdate.getValue();

        if (isNumeric(customerIdText) && isNumeric(customerPhoneText) && customerBirthdate != null) {
            int customerId = Integer.parseInt(customerIdText);
            int customerPhone = Integer.parseInt(customerPhoneText);

            if (service.save(new Customer(customerId, customerPhone, entercustomername.getText(), entercustomersurname.getText(), entercustomeremail.getText(), new Credential("null", "null"), customerBirthdate)) == 1)
                getPrincipalController().showAlertInfo(Constants.USERADDED);
            else {
                getPrincipalController().showAlertError(Constants.USERRNOTADDED);
            }
            principalCargado();
        } else {
            getPrincipalController().showAlertError(Constants.CUSTOMERNOTADDED);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public void principalCargado() {
        customerlist.getItems().addAll(service.getAll().get());
    }
}
