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
import lombok.extern.log4j.Log4j2;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.Pantallas;

import java.io.IOException;

@Log4j2
public class PrincipalController {
    @FXML
    private MenuBar menuPrincipal;
    // objeto especial para DI
    Instance<Object> instance;

    @FXML
    public BorderPane root;

    @Getter
    private String username, password;
    private final Alert alert;


    //constructor
    @Inject
    public PrincipalController(Instance<Object> instance) {
       this.instance = instance;
       alert= new Alert(Alert.AlertType.NONE);
    }


    public void initialize() {
        menuPrincipal.setVisible(false);
        cargarPantalla(Pantallas.LOGIN);
    }


    public void sacarAlertError(String mensaje)
    {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        //alert.getDialogPane().lookupButton(ButtonType.CANCEL).setId("btn-cancel");
        alert.showAndWait();
    }


    private void cargarPantalla(Pantallas pantalla) {
        cambioPantalla(cargarPantalla(pantalla.getRuta()));
    }
    //carga la pantalla de la ruta de recursos indicarda al haber declarado la variable de la pantalla en la clase Pantallas
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
            log.error(e.getMessage(),e);
        }
        return panePantalla;
    }


    private void cambioPantalla(Pane pantallaNueva) {
        root.setCenter(pantallaNueva);
    }


    @FXML
    private void menuClick(ActionEvent actionEvent) {
        switch (((MenuItem)actionEvent.getSource()).getId())
        {
            case "menuItemPantalla1"->{}
            case "menuItemListado"->{}
            case "menuItemPantallaNueva"->{}
            case "menuItemLogout"->{}
        }
    }


    public void onLogin(String username, String password){
        this.username=username;
        this.password=password;
        cargarPantalla(Pantallas.INICIO);
        menuPrincipal.setVisible(true);

    }

    public void setStage(Stage stage) {}
}
