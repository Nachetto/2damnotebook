module javafx.multipantalla {


    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires org.apache.logging.log4j;


    requires jakarta.inject;
    requires jakarta.cdi;
    requires io.vavr;


    exports ui.main to javafx.graphics;
    exports ui.pantallas.principal;
    exports common.config;
    exports ui.pantallas.common;
    exports common;
    exports domain.modelo;
    exports ui.pantallas.login;
    exports ui.pantallas.main;


    opens  ui.pantallas.main to javafx.fxml;
    opens  ui.pantallas.login to javafx.fxml;
    opens domain.modelo to javafx.base;

    opens ui.pantallas.principal;
    opens ui.main;
    opens config;
    opens fxml;

}
