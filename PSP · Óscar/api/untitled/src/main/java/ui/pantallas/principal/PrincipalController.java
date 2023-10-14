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
    private Menu menuPersonajes;
    @FXML
    private MenuItem menuItemListarPersonajes;
    @FXML
    private Menu menuEpisodios;
    @FXML
    private MenuItem menuItemListarEpisodios;
    @FXML
    private Menu menuTemporada;
    @FXML
    private MenuItem menuItemListarTemporadas;
    @FXML
    private MenuBar menuPrincipal;
    Instance<Object> instance;
    @Getter
    @Setter
    ParametrosBusquedaCharacter parametrosBusquedaCharacter;


    @FXML
    public BorderPane root;

    private final Alert alert;


    //constructor
    @Inject
    public PrincipalController(Instance<Object> instance, ParametrosBusquedaCharacter parametrosBusquedaCharacter) {
        this.instance = instance;
        this.parametrosBusquedaCharacter = parametrosBusquedaCharacter;
        alert = new Alert(Alert.AlertType.NONE);
    }


    public void initialize() {
        menuPrincipal.setVisible(true);
        cargarPantalla(Pantallas.INICIO);
    }


    private void showCustomAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        // alert.getDialogPane().lookupButton(ButtonType.CANCEL).setId("btn-cancel");
        alert.showAndWait();
    }

    public void sacarAlertError(String message) {
        showCustomAlert(message, Alert.AlertType.ERROR);
    }

    public void sacarAlertInfo(String message) {
        showCustomAlert(message, Alert.AlertType.INFORMATION);
    }

    private void cargarPantalla(Pantallas pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }

    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
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


    @FXML
    private void menuClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case "menuItemListarPersonajes" -> cargarPantalla(Pantallas.LISTAR_PERSONAJES);
            case "menuItemListarEpisodios" -> cargarPantalla(Pantallas.LISTAR_EPISODIOS);
            case "menuItemListarTemporadas" -> cargarPantalla(Pantallas.LISTAR_TEMPORADAS);
        }
    }

    public void setStage(Stage stage) {
    }
}
