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
import model.Credential;
import service.CustomerService;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.Screens;

import java.io.IOException;

@Log4j2
public class PrincipalController {
    Instance<Object> instance;
    private final Alert alert;
    @Getter
    private String username, password;
    @FXML
    private MenuBar menuPrincipal;
    @FXML
    public BorderPane root;

    @FXML
    private MenuItem menuMainScreen;
    @FXML
    private MenuItem menuItemLogout;
    @FXML
    private Menu menuOptions;
    @FXML
    private MenuItem menuItemPantallaNueva;


    @Inject
    private CustomerService customerService;

    @Inject
    public PrincipalController(Instance<Object> instance) {
        this.instance = instance;
        alert = new Alert(Alert.AlertType.NONE);
    }


    public void initialize() {
        menuPrincipal.setVisible(false);
        loadScreen(Screens.LOGIN);
    }


    public void showAlertError(String mensaje) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        //alert.getDialogPane().lookupButton(ButtonType.CANCEL).setId("btn-cancel");
        alert.showAndWait();
    }

    public void showAlertInfo(String mensaje) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        //alert.getDialogPane().lookupButton(ButtonType.CANCEL).setId("btn-cancel");
        alert.showAndWait();
    }


    private void loadScreen(Screens pantalla) {
        cambioPantalla(loadScreen(pantalla.getRuta()));
    }

    private Pane loadScreen(String ruta) {
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


    public void onLogin(String username, String password) {
        Credential c = new Credential(username, password);
        if (!customerService.checkLogin(c)) {
            showAlertError("Username or password invalid.");
        } else {
            this.username = username;
            loadScreen(Screens.START);
            menuPrincipal.setVisible(true);
        }

    }

    public void onLogout() {
        username = null;
        password = null;
        loadScreen(Screens.LOGIN);
        menuPrincipal.setVisible(false);
    }


    @FXML
    private void menuClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case "menuMainScreen" -> loadScreen(Screens.START);
            case "menuItemLogout" -> onLogout();

            case "menuOrderList" -> loadScreen(Screens.ORDERLIST);
            case "menuOrderAdd" -> loadScreen(Screens.ORDERADD);
            case "menuOrderUpdate" -> loadScreen(Screens.ORDERUPDATE);
            case "menuOrderDelete" -> loadScreen(Screens.ORDERDELETE);

            case "menuCustomerList" -> loadScreen(Screens.CUSTOMERLIST);
            case "menuCustomerAdd" -> loadScreen(Screens.CUSTOMERADD);
            case "menuCustomerUpdate" -> loadScreen(Screens.CUSTOMERUPDATE);
            case "menuCustomerDelete" -> loadScreen(Screens.CUSTOMERDELETE);
        }
    }


    public void setStage(Stage stage) {
    }
}
