package ui.pantallas.main;

import common.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.pantallas.common.BaseScreenController;

public class MainController extends BaseScreenController {
    @FXML
    private Label bienvenida;

    @Override
    public void principalCargado() {

        bienvenida.setText(Constants.BIENVENIDA + getPrincipalController().getUsername().toUpperCase() + "!");
    }
}
