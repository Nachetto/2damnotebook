package ui.screens.customer;

import common.RestaurantError;
import common.UtilitiesCommon;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import service.CustomerServices;
import ui.screens.common.BaseScreenController;

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class CustomerShowController extends BaseScreenController {
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> customerFirstName;
    @FXML
    private TableColumn<Customer, String> customerLastName;
    @FXML
    private TableColumn<Customer, String> customerEmail;
    @FXML
    private TableColumn<Customer, String> customerPhone;
    @FXML
    public TableColumn<Customer, LocalDate> customerDate;
    private final CustomerServices customerServices;

    @Inject
    public CustomerShowController(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }

    @Override
    public void loadMain() {
        Either<RestaurantError, List<Customer>> getall = customerServices.getAll();
        if (getall.isLeft()) {
            log.error(UtilitiesCommon.EMPTY);
            getPrincipalController().showAlertError(UtilitiesCommon.EMPTY);
        } else {
            customerID.setCellValueFactory(new PropertyValueFactory<>("idcustomer"));
            customerFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            customerLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            customerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customerDate.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));


            customerTable.getItems().addAll(getall.get());
        }
    }


}
