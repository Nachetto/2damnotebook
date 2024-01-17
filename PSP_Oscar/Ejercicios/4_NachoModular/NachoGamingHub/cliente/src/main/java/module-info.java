module cliente {
    requires domain;
    requires org.apache.logging.log4j;

    exports ui.main to javafx.graphics;
    exports dao.impl;
    exports ui.pantallas.principal;
    exports dao.retrofit;
    exports usecases.suscripciones;
    exports common.config;
    exports usecases.juegos;
    exports ui.pantallas.main;
    exports ui.pantallas.login;
    exports ui.pantallas.common;
    exports ui.pantallas.juegos;
    exports usecases.usuarios;
    exports common;
    opens ui.main;
    exports ui.pantallas.usuarios;
    opens ui.pantallas.usuarios to javafx.fxml;
    opens ui.pantallas.articulos to javafx.fxml;
    opens ui.pantallas.principal to javafx.fxml;
    opens ui.pantallas.main to javafx.fxml;
    opens ui.pantallas.juegos to javafx.fxml;
    opens dao.retrofit to com.squareup.moshi;
    exports ui.pantallas.articulos;
    exports usecases.articulos;

    requires jakarta.el;
    requires jakarta.inject;
    requires lombok;
    requires io.reactivex.rxjava3;
    requires io.vavr;
    requires retrofit2;
    requires jakarta.cdi;
    requires okhttp3;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.squareup.moshi;
    requires retrofit2.converter.moshi;
    requires retrofit2.adapter.rxjava3;

    opens dao.impl;
}