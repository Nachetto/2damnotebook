package ui.pantallas.customers.update;

import common.Constants;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Customer;
import service.CustomerService;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.customers.common.CustomerCommon;

import java.time.LocalDate;

public class CustomerUpdateController extends BasePantallaController {

    @Inject
    CustomerCommon common;
    @FXML
    private TableView<Customer> customerlist;
    @FXML
    private TableColumn<Customer,Integer> customerphone;
    @FXML
    private  TableColumn<Customer,String> customername;
    @FXML
    private  TableColumn<Customer,String> customersurname;
    @FXML
    private  TableColumn<Customer,String> customeremail;
    @FXML
    private  TableColumn<Customer, LocalDate> customerbirthdate;
    @FXML
    private TableColumn<Customer,Integer> customerid;
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

    public void initialize(){
        common.initializeCustomerTable(customerid,customerphone,customerbirthdate,customeremail,customersurname,customername);
    }

    @Override
    public void principalCargado() {
        customerlist.getItems().addAll(service.getAll().get());
    }

    public void updateCustomer(ActionEvent actionEvent) {
        getPrincipalController().showAlertInfo(Constants.CUSTOMERUPDATED);
    }

    public void selectedUser(MouseEvent mouseEvent) {
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
