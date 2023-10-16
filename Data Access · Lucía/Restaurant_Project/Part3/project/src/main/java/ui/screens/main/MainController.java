package ui.screens.main;

import common.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.screens.common.BaseScreenController;

public class MainController extends BaseScreenController {
    @FXML
    private Label bienvenida;

    @Override
    public void principalCargado() {

        bienvenida.setText(Constants.BIENVENIDA + getPrincipalController().getUsername().toUpperCase() + "!");
    }
}
