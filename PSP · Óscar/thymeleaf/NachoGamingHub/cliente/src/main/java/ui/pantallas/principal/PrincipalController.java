package ui.pantallas.principal;


import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.Pantallas;

import java.io.IOException;

@Log4j2
public class PrincipalController {
    @FXML
    private MenuBar menuPrincipal;
    Instance<Object> instance;
    @Getter
    @Setter
    ParametrosBusquedaCharacter parametrosBusquedaCharacter;
    @Getter
    @Setter
    ParametrosBusquedaEpisode parametrosBusquedaEpisode;
    @FXML
    public BorderPane root;

    @Getter
    public String username = "";


    @Inject
    public PrincipalController(Instance<Object> instance, ParametrosBusquedaCharacter parametrosBusquedaCharacter) {
        this.instance = instance;
        this.parametrosBusquedaCharacter = parametrosBusquedaCharacter;
    }


    public void initialize() {
        menuPrincipal.setVisible(false);
        cargarPantalla(Pantallas.LOGIN);
    }

    public void cargarPantalla(Pantallas pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }

    private Pane cargarPantalla(String ruta) {
        Pane panePantalla = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(ruta));
            BasePantallaController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.principalCargado();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return panePantalla;
    }

    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
    }

    private void showCustomAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        alert.showAndWait();
    }

    public void sacarAlertError(String message) {
        showCustomAlert(message, Alert.AlertType.ERROR);
    }

    public void sacarAlertInfo(String message) {
        showCustomAlert(message, Alert.AlertType.INFORMATION);
    }

    @FXML
    private void menuClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {

            case "menuItemListarJuegos" -> cargarPantalla(Pantallas.LISTAR_JUEGOS);
            case "menuItemListarSuscripciones" -> cargarPantalla(Pantallas.LISTAR_SUSCRIPCIONES);
            case "menuItemListarUsuarios" -> cargarPantalla(Pantallas.LISTAR_USUARIOS);
            default -> sacarAlertError("Error al cargar la pantalla");
        }
    }

    public void onLogin(TextField username) {
        menuPrincipal.setVisible(true);
        cargarPantalla(Pantallas.INICIO);
        this.username = username.getText();
    }

    public void setStage(Stage stage) {
        //habria que hacer esto abstracto
    }
}
