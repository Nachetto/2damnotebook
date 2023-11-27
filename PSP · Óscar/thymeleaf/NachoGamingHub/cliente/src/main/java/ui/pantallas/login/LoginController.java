package ui.pantallas.login;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ui.pantallas.common.BasePantallaController;
import usecases.usuarios.LoginUseCase;

public class LoginController extends BasePantallaController {

    @FXML
    public TextField username;
    @FXML
    public PasswordField password;

    private LoginUseCase loginUseCase;
    @Inject
    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }


    public void login() {
        loginUseCase.login(username.getText(),password.getText())
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(either -> {
                    if (either.isRight()) {
                        System.out.println(either.get());
                        getPrincipalController().onLogin(username);
                    } else if (either.isLeft()) {
                        getPrincipalController().sacarAlertError(either.getLeft().getMensaje());
                    }
                });
    }
}
