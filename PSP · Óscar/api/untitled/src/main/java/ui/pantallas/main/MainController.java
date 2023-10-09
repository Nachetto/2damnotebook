package ui.pantallas.main;

import common.Constantes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.pantallas.common.BasePantallaController;

public class MainController extends BasePantallaController {
    @FXML
    private Label title;

    @Override
    public void principalCargado() {
        title.setText(Constantes.TITULO_APP);
    }
}
