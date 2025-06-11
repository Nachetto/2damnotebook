package ui.screens.login;

import common.RestaurantError;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Credentials;
import service.CredentialsServices;
import ui.screens.common.BaseScreenController;

public class LoginController extends BaseScreenController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    private final CredentialsServices credentialsServices;

    @Inject
    public LoginController(CredentialsServices credentialsServices) {
        this.credentialsServices = credentialsServices;
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        Credentials credentials = new Credentials(0, username.getText(), password.getText());
        Either<RestaurantError,Credentials> login = credentialsServices.login(credentials);
        if (login.isRight()){
            getPrincipalController().onLogin(login.get());
        } else {
            getPrincipalController().showAlertError(login.getLeft().getMessage());
            getPrincipalController().onLogout();
        }
    }

    @Override
    public void loadMain() {}
}
