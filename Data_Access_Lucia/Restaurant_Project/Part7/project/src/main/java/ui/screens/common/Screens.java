package ui.screens.common;


public enum Screens {
    LOGIN("/fxml/login.fxml"),
    START("/fxml/main.fxml"),
    ORDERLIST("/fxml/order/list.fxml"),
    ORDERADD("/fxml/order/add.fxml"),
    ORDERUPDATE("/fxml/order/update.fxml"),
    ORDERDELETE("/fxml/order/delete.fxml"),
    CUSTOMERADD("/fxml/customer/customeradd.fxml"),
    CUSTOMERLIST("/fxml/customer/customerlist.fxml"),
    CUSTOMERUPDATE("/fxml/customer/customerupdate.fxml"),
    CUSTOMERDELETE("/fxml/customer/customerdelete.fxml"),

    ;

    private final String ruta;

    Screens(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }

}