package ui.screens.customer;

import common.RestaurantError;
import ui.screens.common.UtilitiesScreens;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Credentials;
import model.Customer;
import service.CustomerServices;
import ui.screens.common.BaseScreenController;


public class CustomerAddController extends BaseScreenController {

    @FXML
    private TextField addFirstName;
    @FXML
    private TextField addLastName;
    @FXML
    private TextField addEmail;
    @FXML
    private TextField addPhone;
    @FXML
    private DatePicker addDate;
    @FXML
    private TextField addUsername;
    @FXML
    private PasswordField addPassword;
    private final CustomerServices customerServices;

    @Inject
    public CustomerAddController(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }

    public void checkFields(){
        if (addFirstName.getText() != null
                && addLastName.getText() != null
                && addEmail.getText() != null
                && addDate.getValue() != null
                && addPhone.getText() != null
                && addUsername.getText() != null
                && addPassword.getText() != null) {
            add();
        } else {//missing fields to fill out
            getPrincipalController().showAlertInformation(UtilitiesScreens.ADDCUSTOMERMISSING);
        }
    }
    public void add() {
        Credentials credentials = new Credentials(null, addUsername.getText(), addPassword.getText());
        Customer customer = new Customer(null, addFirstName.getText(), addLastName.getText(), addEmail.getText(), addPhone.getText(), addDate.getValue(), credentials);

        Either<RestaurantError, Integer> add = customerServices.add(customer);
        if (add.isRight()) {
            getPrincipalController().showAlertInformation(UtilitiesScreens.ADDUSER);
        } else {//failure to add the customer
            getPrincipalController().showAlertError(add.getLeft().getMessage());
        }
    }

    @Override
    public void loadMain() {}
}
