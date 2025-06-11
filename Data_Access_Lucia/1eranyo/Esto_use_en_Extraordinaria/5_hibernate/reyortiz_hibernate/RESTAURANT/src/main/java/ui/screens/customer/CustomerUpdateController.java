package ui.screens.customer;

import common.RestaurantError;
import ui.screens.common.UtilitiesScreens;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;
import model.Credentials;
import model.Customer;
import service.CustomerServices;
import ui.screens.common.BaseScreenController;

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class CustomerUpdateController extends BaseScreenController {


    @FXML
    private TextField updateFirstName;
    @FXML
    private TextField updateLastName;
    @FXML
    private TextField updateEmail;
    @FXML
    private TextField updatePhone;
    @FXML
    private DatePicker updateDate;
    @FXML
    private TableView<Customer> updateTable;
    @FXML
    private TableColumn<Customer, Integer> updateTableID;
    @FXML
    private TableColumn<Customer, String> updateTableFirstName;
    @FXML
    private TableColumn<Customer, String> updateTableLastName;
    @FXML
    private TableColumn<Customer, String> updateTableEmail;
    @FXML
    private TableColumn<Customer, String> updateTablePhone;
    @FXML
    private TableColumn<Customer, LocalDate> updateTableDate;

    private final CustomerServices customerServices;

    @Inject
    public CustomerUpdateController(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }


    @Override
    public void loadMain() {
        Either<RestaurantError, List<Customer>> list = customerServices.getAll();
        if (list.isLeft()) {
            getPrincipalController().showAlertError(list.getLeft().getMessage());
        } else {
            updateTableID.setCellValueFactory(new PropertyValueFactory<>("idcustomer"));
            updateTableFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            updateTableLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            updateTableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            updateTablePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            updateTableDate.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

            updateTable.getItems().addAll(list.get());
        }
    }

    public void showData() {
        updateFirstName.setText(updateTable.getSelectionModel().getSelectedItem().getFirstName());
        updateLastName.setText(updateTable.getSelectionModel().getSelectedItem().getLastName());
        updateEmail.setText(updateTable.getSelectionModel().getSelectedItem().getEmail());
        updatePhone.setText(updateTable.getSelectionModel().getSelectedItem().getPhone());
        updateDate.setValue(updateTable.getSelectionModel().getSelectedItem().getDateOfBirth());
    }

    public void checkFields() {
        if (updateFirstName.getText() != null
                && updateLastName.getText() != null
                && updateEmail.getText() != null
                && updateDate.getValue() != null
                && updatePhone.getText() != null) {
            update();
        } else {
            getPrincipalController().showAlertInformation(UtilitiesScreens.ADDCUSTOMERMISSING);
        }
    }

    public void update() {
        Credentials credentials = null;
        Customer customer = new Customer(updateTable.getSelectionModel().getSelectedItem().getIdcustomer(), updateFirstName.getText(), updateLastName.getText(), updateEmail.getText(), updatePhone.getText(), updateDate.getValue(),credentials);

        Either<RestaurantError, Integer> update = customerServices.update(customer);
        if (update.isRight()) {
            getPrincipalController().showAlertInformation(UtilitiesScreens.UPDATEUSER);
        } else {
            getPrincipalController().showAlertError(update.getLeft().getMessage());
        }

        clear();
        loadMain();

    }
    public void clear(){
        updateFirstName.clear();
        updateLastName.clear();
        updateEmail.clear();
        updatePhone.clear();
        updateDate.setValue(null);
        updateTable.getItems().clear();
    }

}
