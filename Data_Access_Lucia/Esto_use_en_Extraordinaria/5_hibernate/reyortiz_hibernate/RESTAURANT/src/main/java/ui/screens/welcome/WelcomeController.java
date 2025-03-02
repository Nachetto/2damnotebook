package ui.screens.welcome;

import ui.screens.common.UtilitiesScreens;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.screens.common.BaseScreenController;

public class WelcomeController extends BaseScreenController {
    @FXML
    private Label welcome;

    @Override
    public void loadMain() {
        welcome.setText(UtilitiesScreens.WELCOME + getPrincipalController().getUsername());
    }

}
