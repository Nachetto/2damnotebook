package ui.pantallas.character;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
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
    @FXML
    private ComboBox<String> comboBoxOpcionesABuscar;
    @FXML
    private TextField textFieldConArgumentoDeBusqueda;

    @Override
    public void principalCargado() {
        titleLabel.setText("Characters");
        configurarEscuchadorCheckBox();
        darValorAlSpinner();
        configurarEscuchadorComboBox();
    }

    private void configurarEscuchadorCheckBox() {
        checkBox.setSelected(true);
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> spinner.setDisable(!newValue));
    }

    private void darValorAlSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinner.setValueFactory(valueFactory);
    }

    private void configurarEscuchadorComboBox() {
        textFieldConArgumentoDeBusqueda.setDisable(true);
        comboBoxOpcionesABuscar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> cambiarPromptText(newValue));
    }

    private void cambiarPromptText(String newValue) {
        if (newValue != null) {
            if (newValue.equalsIgnoreCase("Empty Search")) {
                textFieldConArgumentoDeBusqueda.setDisable(true);
                textFieldConArgumentoDeBusqueda.setPromptText("Show all Characters");
            } else {
                textFieldConArgumentoDeBusqueda.setDisable(false);
                textFieldConArgumentoDeBusqueda.setEditable(true);
                textFieldConArgumentoDeBusqueda.setPromptText("Write " + newValue);
                textFieldConArgumentoDeBusqueda.requestFocus();
            }
        } else {
            textFieldConArgumentoDeBusqueda.setPromptText("Search By");
        }
    }

    public void realizarLlamada() {
        ParametrosBusquedaCharacter parametros = new ParametrosBusquedaCharacter();

        if (checkBox.isSelected()) {
            parametros.setLimite(spinner.getValue());
        } else {
            parametros.setLimite(0);
        }

        if (comboBoxOpcionesABuscar.getSelectionModel().getSelectedItem() != null) {
            parametros.setTipoBusqueda(comboBoxOpcionesABuscar.getSelectionModel().getSelectedItem());
            if (parametros.getTipoBusqueda().equalsIgnoreCase("Empty Search")) {
                getPrincipalController().sacarAlertInfo(ConstantesPantallas.PARAMETROS_GUARDADOS);
            } else {
                if (textFieldConArgumentoDeBusqueda.getText() == null || textFieldConArgumentoDeBusqueda.getText().isEmpty()) {
                    getPrincipalController().sacarAlertError(ConstantesPantallas.SELECCIONE_UNA_OPCION);
                } else {
                    parametros.setArgumentoBusqueda(textFieldConArgumentoDeBusqueda.getText());
                    getPrincipalController().sacarAlertInfo(ConstantesPantallas.PARAMETROS_GUARDADOS);
                }
            }
        } else {
            getPrincipalController().sacarAlertError(ConstantesPantallas.SELECCIONE_UNA_OPCION_DE_BUSQUEDA);
        }
    }


}
