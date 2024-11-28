package ui.screens.login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.screens.common.BaseScreenController;

public class LoginController extends BaseScreenController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private void login() {
        getPrincipalController().onLogin(username.getText(), password.getText());
    }
}[{\[{\[{}]}]}]
