package ui.pantallas.main;

import common.Constantes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.pantallas.common.BasePantallaController;

public class MainController extends BasePantallaController {
    @FXML
    private Label bienvenida;

    @Override
    public void principalCargado() {

        bienvenida.setText(Constantes.BIENVENIDA +getPrincipalController().getUsername().toUpperCase()+"!");
    }
}
