package ui.pantallas.customers.delete;

import common.Constants;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.Customer;
import service.CustomerService;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.customers.common.CustomerCommon;

import java.time.LocalDate;

public class CustomerDeleteController extends BasePantallaController {

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
    private Customer c;

    @Inject
    private CustomerService service;

    public void initialize() {
        common.initializeCustomerTable(customerid, customerphone, customerbirthdate, customeremail, customersurname, customername);
    }

    public void selectedUser(MouseEvent mouseEvent) {
        c = customerlist.getSelectionModel().getSelectedItem();
    }

    @Override
    public void principalCargado() {
        customerlist.getItems().addAll(service.getAll().get());
    }

    public void deleteUser(ActionEvent actionEvent) {
        if (service.getAll().get().remove(c))
            getPrincipalController().showAlertInfo(Constants.CUSTOMERDELETED);
        else getPrincipalController().showAlertError(Constants.CUSTOMERNOTDELETED);
    }
}
