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

    exports ui.screens.customers.add;
    exports ui.screens.customers.delete;
    exports ui.screens.customers.list;
    exports ui.screens.customers.update;
    exports ui.main to javafx.graphics;
    exports ui.screens.orders.add;
    exports ui.screens.principal;
    exports ui.screens.orders.delete;
    exports ui.screens.orders.list;
    exports ui.screens.orders.update;
    exports common.config;
    exports ui.screens.common;
    exports common;
    exports ui.screens.login;
    exports ui.screens.main;
    exports dao.impl;
    exports service;
    exports ui.screens.customers.common;
    exports ui.screens.orders.common;
    exports model;

    opens ui.screens.orders.update;
    opens ui.screens.orders.delete;
    opens ui.screens.orders.add;
    opens ui.screens.orders.list;
    opens ui.screens.customers.update;
    opens ui.screens.customers.add;
    opens ui.screens.customers.delete;
    opens ui.screens.main to javafx.fxml;
    opens ui.screens.login to javafx.fxml;
    opens ui.screens.customers.list;
    opens ui.screens.principal;
    opens ui.main;
    opens common.config;
    opens fxml;
    opens service;
    opens model;
}
