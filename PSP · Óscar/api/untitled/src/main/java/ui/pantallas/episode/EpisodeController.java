package ui.pantallas.episode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;
import ui.pantallas.principal.ParametrosBusquedaCharacter;
import ui.pantallas.principal.PrincipalController;
import ui.pantallas.principal.ParametrosBusquedaEpisode;

public class EpisodeController extends BasePantallaController {

    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private CheckBox checkBox;
    @FXML
    private TextField textFieldConArgumentoDeBusqueda;
    @FXML
    private ComboBox<String> comboBoxOpcionesABuscar;
    @FXML
    private Label titleLabel;

    @Override
    public void principalCargado() {
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
        PrincipalController.cambiandoElPrompText(newValue, textFieldConArgumentoDeBusqueda);
    }



    public void realizarLlamada(ActionEvent actionEvent) {
        ParametrosBusquedaEpisode parametros = new ParametrosBusquedaEpisode();
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
        getPrincipalController().setParametrosBusquedaEpisode(parametros);
    }
}