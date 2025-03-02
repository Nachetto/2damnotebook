package ui.screens.common;

import lombok.Getter;
import ui.screens.principal.PrincipalController;


@Getter
public abstract class BaseScreenController {

    private PrincipalController principalController;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public abstract void loadMain();
}
