package ui.pantallas.customers.common;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.time.LocalDate;

public class CustomerCommon {
    public void initializeCustomerTable(TableColumn<Customer, Integer> id, TableColumn<Customer, Integer> phone, TableColumn<Customer, LocalDate> birthdate, TableColumn<Customer, String> email, TableColumn<Customer, String> surname, TableColumn<Customer, String> name) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}
