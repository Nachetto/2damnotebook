package ui.pantallas.character;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;
import ui.pantallas.principal.ParametrosBusquedaCharacter;

public class CharacterController extends BasePantallaController {

    @FXML
    private Label titleLabel;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Spinner<Integer> spinner;
    @Override
    public void principalCargado() {
        titleLabel.setText("Characters");
        configurarEscuchadorCheckBox();
        darValorAlSpinner();
    }

    private void configurarEscuchadorCheckBox() {
        checkBox.setSelected(true);
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> spinner.setDisable(!newValue));
    }

    private void darValorAlSpinner() {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 83, 1));
    }


    public void realizarLlamada() {
        ParametrosBusquedaCharacter parametros = new ParametrosBusquedaCharacter();

        if (checkBox.isSelected())
            parametros.setLimite(spinner.getValue());
        else
            parametros.setLimite(83);

        getPrincipalController().setParametrosBusquedaCharacter(parametros);
        getPrincipalController().sacarAlertInfo(ConstantesPantallas.PARAMETROS_GUARDADOS);
    }


}
