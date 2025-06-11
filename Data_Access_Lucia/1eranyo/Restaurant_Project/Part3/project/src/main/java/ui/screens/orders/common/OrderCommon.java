package ui.screens.orders.common;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;

import java.time.LocalDateTime;

public class OrderCommon {
    public void initializeCustomerTable(TableColumn<Order, Integer> orderid, TableColumn<Order, Integer> tableid, TableColumn<Order, Integer> customerid, TableColumn<Order, LocalDateTime> orderdate) {
        orderid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        tableid.setCellValueFactory(new PropertyValueFactory<>("tableid"));
        customerid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        orderdate.setCellValueFactory(new PropertyValueFactory<>("orderdate"));
    }
}
