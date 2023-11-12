package ui.pantallas.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

public class MainController extends BasePantallaController {
    @FXML
    private Label description;
    @FXML
    private Label title;

    @Override
    public void principalCargado() {
        title.setText(ConstantesPantallas.TITULO_APP);
        description.setText(ConstantesPantallas.DESCRIPCION_APP);
    }
}
