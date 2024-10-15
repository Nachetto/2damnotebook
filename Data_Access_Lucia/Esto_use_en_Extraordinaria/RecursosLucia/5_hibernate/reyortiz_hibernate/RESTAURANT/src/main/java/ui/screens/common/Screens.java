package ui.screens.common;

import lombok.Getter;

@Getter
public enum Screens {


    LOGIN("/fxml/login.fxml"),
    WELCOME("/fxml/welcome.fxml"),
    CUSTOMERSHOW("/fxml/customer/customerShow.fxml"),
    CUSTOMERADD("/fxml/customer/customerAdd.fxml"),
    CUSTOMERUPDATE("/fxml/customer/customerUpdate.fxml"),
    CUSTOMERDELETE("/fxml/customer/customerDelete.fxml"),
    ORDERSHOW("/fxml/order/orderShow.fxml"),
    ORDERADD("/fxml/order/orderAdd.fxml"),
    ORDERUPDATE("/fxml/order/orderUpdate.fxml"),
    ORDERDELETE("/fxml/order/orderDelete.fxml");


    private final String path;

    Screens(String path) {
        this.path = path;
    }


}
