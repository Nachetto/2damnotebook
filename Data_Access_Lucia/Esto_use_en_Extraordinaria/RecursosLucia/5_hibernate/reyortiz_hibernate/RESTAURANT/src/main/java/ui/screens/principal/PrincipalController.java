package ui.screens.principal;


import common.UtilitiesCommon;
import dao.common.connections.DBConnectionPool;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import model.Credentials;
import ui.screens.common.BaseScreenController;
import ui.screens.common.Screens;
import ui.screens.common.UtilitiesScreens;

import java.io.IOException;
import java.util.Optional;


@Log4j2
public class PrincipalController {

    Instance<Object> instance;

    @FXML
    private MenuBar adminMenu;
    @FXML
    private MenuItem menuItemOrderDelete;
    @FXML
    private Menu menuCustomer;
    @FXML
    public MenuItem menuItemOrderAdd;
    private Stage primaryStage;
    @FXML
    public BorderPane root;
    private final Alert alert;
    @Getter
    private String username;
    @Getter
    private int loginId;
    private final DBConnectionPool dbConnectionPool;



    @Inject
    public PrincipalController(Instance<Object> instance, DBConnectionPool dbConnectionPool) {
        this.instance = instance;
        this.dbConnectionPool = dbConnectionPool;
        alert = new Alert(Alert.AlertType.NONE);
    }

    @FXML
    private void initialize() {
        adminMenu.setVisible(false);
        screenLoad(Screens.LOGIN);
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void screenLoad(Screens screen) {
        screenChange(screenLoad(screen.getPath()));
    }

    private Pane screenLoad(String route) {
        Pane panePantalla = null;
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(route));
            BaseScreenController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.loadMain();


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return panePantalla;
    }

    private void screenChange(Pane newScreen) {
        root.setCenter(newScreen);
    }

    public void onLogin(Credentials credentials) {
        this.username = credentials.getUsername();
        this.loginId = credentials.getIdcredentials();
        screenLoad(Screens.WELCOME);
        adminMenu.setVisible(true);
        if (credentials.getIdcredentials() < 0){
            menuItemOrderAdd.setVisible(false);
            menuCustomer.setVisible(true);
            menuItemOrderDelete.setVisible(true);
        } else {
            menuItemOrderAdd.setVisible(true);
            menuCustomer.setVisible(false);
            menuItemOrderDelete.setVisible(false);
        }

    }


    public void onLogout() {
        this.username = null;
        screenLoad(Screens.LOGIN);
        adminMenu.setVisible(false);
    }

    public void exit() {
        primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        dbConnectionPool.closePool();
    }

    public void closeWindowEvent(WindowEvent windowEvent){
        dbConnectionPool.closePool();
    }
    public void showAlertError(String message) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.getDialogPane().setId(UtilitiesScreens.ALERT_ERROR);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId(UtilitiesScreens.BTN_OK);
        alert.showAndWait();
    }

    public void showAlertInformation(String message) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.getDialogPane().setId(UtilitiesScreens.ALERT_INFORMATION);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId(UtilitiesScreens.BTN_OK);
        alert.showAndWait();
    }

    public boolean showAlertConfirmation(String message) {
        boolean confirm = false;
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.getDialogPane().setId(UtilitiesScreens.ALERT_CONFIRMATION);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId(UtilitiesScreens.BTN_OK);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            confirm = true;
        }
        return confirm;
    }

    @FXML
    private void customerMenuClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case "menuItemCustomerShow" -> screenLoad(Screens.CUSTOMERSHOW);
            case "menuItemCustomerAdd" -> screenLoad(Screens.CUSTOMERADD);
            case "menuItemCustomerUpdate" -> screenLoad(Screens.CUSTOMERUPDATE);
            case "menuItemCustomerDelete" -> screenLoad(Screens.CUSTOMERDELETE);
            default -> {
                screenLoad(Screens.WELCOME);
                showAlertError(UtilitiesCommon.MENUFAILURE);
            }
        }
    }

    @FXML
    private void orderMenuClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case "menuItemOrderShow" -> screenLoad(Screens.ORDERSHOW);
            case "menuItemOrderAdd" -> screenLoad(Screens.ORDERADD);
            case "menuItemOrderUpdate" -> screenLoad(Screens.ORDERUPDATE);
            case "menuItemOrderDelete" -> screenLoad(Screens.ORDERDELETE);
            default -> {
                screenLoad(Screens.WELCOME);
                showAlertError(UtilitiesCommon.MENUFAILURE);
            }
        }
    }
}
