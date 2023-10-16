package ui.screens.orders.list;

import common.Constants;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Order;
import model.OrderItem;
import service.CustomerService;
import service.MenuItemService;
import service.OrderItemService;
import service.OrderService;
import ui.screens.common.BaseScreenController;
import ui.screens.orders.common.OrderCommon;

import model.MenuItem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListController extends BaseScreenController {
    @FXML
    private TextField customertextfield;
    @FXML
    private TableView<OrderItem> orderitemlist;
    @FXML
    private TableColumn<OrderItem, Integer> order_item_id;
    @FXML
    private TableColumn<OrderItem, Integer> order_id;
    @FXML
    private TableColumn<OrderItem, Integer> menu_item_id;
    @FXML
    private TableColumn<OrderItem, Integer> quantity;
    @FXML
    private TableColumn<Order, Integer> orderid;
    @FXML
    private TableColumn<Order, Integer> tableid;
    @FXML
    private TableColumn<Order, Integer> customerid;
    @FXML
    private TableColumn<Order, LocalDateTime> orderdate;
    @Inject
    private OrderCommon common;
    @FXML
    private TableView<Order> orderlist;
    @Inject
    private OrderService service;
    @Inject
    private OrderItemService service2;
    @Inject
    private CustomerService service3;
    @Inject
    private MenuItemService menuItemService;
    private final List<MenuItem> menuItems = new ArrayList<>();
    public void initialize() {
        common.initializeCustomerTable(orderid, tableid, customerid, orderdate);
        order_item_id.setCellValueFactory(new PropertyValueFactory<>("order_item_id"));
        order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        menu_item_id.setCellValueFactory(new PropertyValueFactory<>("menu_item_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @Override
    public void principalCargado() {
        if (service.getAll().isLeft()) {
            getPrincipalController().showAlertError(service.getAll().getLeft());
        } else {
            orderlist.getItems().addAll(service.getAll().get());
            customertextfield.setEditable(false);
        }

        // Carga los detalles de MenuItem en la lista menuItems
        loadMenuItems();
    }



    public void selectedUser() {
        orderitemlist.getItems().clear();
        Order selectedOrder = orderlist.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            if (service2.getAll().isRight()) {
                List<OrderItem> selectedOrderItems = service2.getAll().get().stream()
                        .filter(orderItem -> orderItem.getOrder_id() == selectedOrder.getOrderid())
                        .toList();

                // Obtener los detalles de MenuItem y actualizar la lista de OrderItem
                selectedOrderItems.forEach(orderItem -> {
                    MenuItem menuItem = findMenuItemById(orderItem.getMenu_item_id());
                    if (menuItem != null) {
                        orderItem.setMenu_item_id(menuItem.getId());
                    }
                });

                orderitemlist.getItems().addAll(selectedOrderItems);

                Order o = orderlist.getSelectionModel().getSelectedItem();
                Either<String, Customer> result = service3.get(o.getCustomerid());

                if (result.isRight()) {
                    Customer customer = result.get();
                    String name = customer.getName();
                    customertextfield.setText(name);
                }
            } else {
                getPrincipalController().showAlertError(Constants.ORDERITEMDBERROR + service2.getAll().getLeft());
            }
        }
    }

    private void loadMenuItems() {
        // Aquí obtén y carga los detalles de MenuItem en la lista menuItems
        Either<String, List<MenuItem>> menuItemsResult = menuItemService.getAll();
        if (menuItemsResult.isRight()) {
            menuItems.addAll(menuItemsResult.get());
        } else {
            // Maneja el caso en el que no se pueden cargar los detalles de MenuItem
            getPrincipalController().showAlertError(Constants.MENUITEMSXMLREADERROR + menuItemsResult.getLeft());
        }
    }

    private MenuItem findMenuItemById(int menuItemId) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getId() == menuItemId) {
                return menuItem;
            }
        }
        return null; // Retorna null si no se encuentra el MenuItem
    }



}
