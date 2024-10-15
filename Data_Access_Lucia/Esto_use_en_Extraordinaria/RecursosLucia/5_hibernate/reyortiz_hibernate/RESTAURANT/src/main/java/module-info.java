module javafx {

//REQUIRE
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires lombok;
    requires jakarta.inject;
    requires jakarta.cdi;
    requires org.apache.logging.log4j;
    requires io.vavr;
    requires jakarta.xml.bind;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires spring.jdbc;
    requires spring.tx;
    requires org.glassfish.jaxb.runtime;
    requires java.logging;
    requires commons.dbcp2;
    requires jakarta.persistence;
    requires jakarta.annotation;

//EXPORT
    exports ui.main to javafx.graphics;
    exports ui.screens.principal;
    exports ui.screens.common;
    exports ui.screens.login;
    exports ui.screens.welcome;
    exports ui.screens.customer;
    exports ui.screens.orders;
    exports common;
    exports service;
    exports model;
    exports dao.impl;
    exports dao.common;
    exports dao.staticlist.impl;
    exports dao.staticlist;
    exports model.xml;

//OPEN
    opens ui.screens.principal;
    opens ui.screens.login to javafx.fxml;
    opens ui.screens.welcome to javafx.fxml;
    opens ui.screens.customer to javafx.fxml;
    opens ui.screens.orders;
    opens ui.screens.common;
    opens ui.main;
    opens css;
    opens fxml;
    opens xml;
    opens common;
    opens configFiles;
    opens model.xml;
    opens dao.common;
    exports dao;
    opens dao;

    opens model to org.hibernate.orm.core;
    exports dao.common.connections;
    opens dao.common.connections;


}
