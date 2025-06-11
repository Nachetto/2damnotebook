package ui.screens.principal;


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
import ui.screens.common.BaseScreenController;
import ui.screens.common.Screens;

import java.io.IOException;
import java.util.Optional;

@Log4j2
public class PrincipalController {
    private final Instance<Object> instance;
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
    }


    public void initialize() {
        menuPrincipal.setVisible(false);
        loadScreen(Screens.LOGIN);
    }

    private void showCustomAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        // alert.getDialogPane().lookupButton(ButtonType.CANCEL).setId("btn-cancel");
        alert.showAndWait();
    }

    public void showAlertError(String message) {
        showCustomAlert(message, Alert.AlertType.ERROR);
    }

    public void showAlertInfo(String message) {
        showCustomAlert(message, Alert.AlertType.INFORMATION);
    }

    public boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.getDialogPane().setId("alert");
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("btn-ok");
        alert.getDialogPane().lookupButton(ButtonType.CANCEL).setId("btn-cancel");
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setDefaultButton(true);
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDefaultButton(false);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
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
            BaseScreenController pantallaController = fxmlLoader.getController();
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
            case "menuOrderAdd" ->{//ADMIN NO
                if (username.equalsIgnoreCase("root"))
                    showAlertError("You can't add orders");
                else
                    loadScreen(Screens.ORDERADD);
            }
            case "menuOrderUpdate" -> loadScreen(Screens.ORDERUPDATE);
            case "menuOrderDelete" ->{//CUSTOMER NO
                if (username.equalsIgnoreCase("root"))
                    loadScreen(Screens.ORDERDELETE);
                else
                    showAlertError("You can't delete orders");
            }

            case "menuCustomerList" ->{
                if (username.equalsIgnoreCase("root"))
                    loadScreen(Screens.CUSTOMERLIST);
                else
                    showAlertError("You can't list Customers");
            }
            case "menuCustomerAdd" ->{
                if (username.equalsIgnoreCase("root")||username.equalsIgnoreCase("john"))
                    loadScreen(Screens.CUSTOMERADD);
                else
                    showAlertError("You can't add Customers");
            }
            case "menuCustomerUpdate" ->{
                if (username.equalsIgnoreCase("root"))
                    loadScreen(Screens.CUSTOMERUPDATE);
                else
                    showAlertError("You can't update Customers");
            }
            case "menuCustomerDelete" ->{
                if (username.equalsIgnoreCase("root"))
                    loadScreen(Screens.CUSTOMERDELETE);
                else
                    showAlertError("You can't delete Customers");
            }
        }
    }


    public void setStage(Stage stage) {//TODO(MNAKE THIS STATIC)
    }
}
