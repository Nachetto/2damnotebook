module javafx.multipantalla {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.logging.log4j;
    requires jakarta.inject;
    requires jakarta.cdi;
    requires io.vavr;
    requires jakarta.xml.bind;

    exports ui.pantallas.customers.add;
    exports ui.pantallas.customers.delete;
    exports ui.pantallas.customers.list;
    exports ui.pantallas.customers.update;
    exports ui.main to javafx.graphics;
    exports ui.pantallas.orders.add;
    exports ui.pantallas.principal;
    exports ui.pantallas.orders.delete;
    exports ui.pantallas.orders.list;
    exports ui.pantallas.orders.update;
    exports common.config;
    exports ui.pantallas.common;
    exports common;
    exports ui.pantallas.login;
    exports ui.pantallas.main;
    exports dao.impl;
    exports service;
    exports ui.pantallas.customers.common;
    exports ui.pantallas.orders.common;
    exports model;

    opens ui.pantallas.orders.update;
    opens ui.pantallas.orders.delete;
    opens ui.pantallas.orders.add;
    opens ui.pantallas.orders.list;
    opens ui.pantallas.customers.update;
    opens ui.pantallas.customers.add;
    opens ui.pantallas.customers.delete;
    opens ui.pantallas.main to javafx.fxml;
    opens ui.pantallas.login to javafx.fxml;
    opens ui.pantallas.customers.list;
    opens ui.pantallas.principal;
    opens ui.main;
    opens common.config;
    opens fxml;
    opens service;
    opens model to javafx.base;
}
