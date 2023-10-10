module javafx.multipantalla {


    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires org.apache.logging.log4j;


    requires jakarta.inject;
    requires jakarta.cdi;
    requires io.vavr;
    requires moshi;
    requires okhttp3;
    requires retrofit2;
    requires retrofit2.adapter.rxjava3;
    requires retrofit2.converter.moshi;
    requires io.reactivex.rxjava3;

    exports dao.retrofit.modelo;
    exports ui.main to javafx.graphics;
    exports ui.pantallas.principal;
    exports common.config;
    exports ui.pantallas.common;
    exports common;
    exports domain.modelo;
    exports ui.pantallas.main;
    exports dao.retrofit;
    exports dao.impl;
    exports service;

    opens  ui.pantallas.main to javafx.fxml;
    opens domain.modelo to javafx.base;
    opens dao.retrofit.modelo;

    opens ui.pantallas.principal;
    opens ui.main;
    opens config;
    opens fxml;

}
