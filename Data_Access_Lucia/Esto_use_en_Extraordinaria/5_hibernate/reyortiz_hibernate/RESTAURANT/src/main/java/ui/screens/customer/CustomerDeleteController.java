package ui.screens.customer;

import common.RestaurantError;
import ui.screens.common.UtilitiesScreens;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.RestaurantOrder;
import service.CustomerServices;
import service.RestaurantOrderServices;
import ui.screens.common.BaseScreenController;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class CustomerDeleteController extends BaseScreenController {

    //Customer list
    @FXML
    private TableView<Customer> deleteTable;
    @FXML
    private TableColumn<Customer, Integer> deleteTableID;
    @FXML
    private TableColumn<Customer, String> deleteTableLastName;
    @FXML
    private TableColumn<Customer, String> deleteTableFirstName;
    @FXML
    private TableColumn<Customer, String> deleteTableEmail;
    @FXML
    private TableColumn<Customer, String> deleteTablePhone;
    @FXML
    private TableColumn<Customer, LocalDate> deleteTableDate;

    //Associated orders list
    @FXML
    private TableView<RestaurantOrder> deleteTableOrder;
    @FXML
    private TableColumn<RestaurantOrder, Integer> orderId;
    @FXML
    private TableColumn<RestaurantOrder, String> orderResTable;
    @FXML
    private TableColumn<RestaurantOrder, String> orderDate;

    private final CustomerServices customerServices;
    private final RestaurantOrderServices restaurantOrderServices;

    @Inject
    public CustomerDeleteController(CustomerServices customerServices, RestaurantOrderServices restaurantOrderServices) {
        this.customerServices = customerServices;
        this.restaurantOrderServices = restaurantOrderServices;
    }


    @Override
    public void loadMain() {
        //costumers
        deleteTableID.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.IDCUSTOMER));
        deleteTableFirstName.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.FIRST_NAME));
        deleteTableLastName.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.LAST_NAME));
        deleteTableEmail.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.EMAIL));
        deleteTablePhone.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.PHONE));
        deleteTableDate.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.DATE_OF_BIRTH));

        deleteTable.getItems().addAll(customerServices.getAll().getOrNull());

        //associated orders
        orderId.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.ID));
        orderResTable.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.TABLE_NUMBER));
        orderDate.setCellValueFactory(new PropertyValueFactory<>(UtilitiesScreens.DATE));

        //deleteTableOrder.getItems().addAll(restaurantOrderServices.getAll().getOrNull());

    }

    public void showOrders() {
        if (deleteTable.getSelectionModel().getSelectedItem() != null) {
            deleteTableOrder.getItems().clear();
            Either<RestaurantError, List<RestaurantOrder>> getAll = restaurantOrderServices.getAll(deleteTable.getSelectionModel().getSelectedItem().getIdcustomer());
            if (getAll.isRight()){
                deleteTableOrder.getItems().addAll(getAll.get());
            } else {
                getPrincipalController().showAlertInformation(getAll.getLeft().getMessage());
            }
        }
    }

    public void delete() {
        if (deleteTable.getSelectionModel().getSelectedItem() != null) { //making sure a customer is selected

            Either <RestaurantError, Integer> unconfirmedDelete = customerServices.delete(deleteTable.getSelectionModel().getSelectedItem(), false);

            if (unconfirmedDelete.isLeft()){
                deleteWithOrders();
            } else {
                getPrincipalController().showAlertInformation(UtilitiesScreens.DELETEUSER);
                refresh();
            }
        } else {
            getPrincipalController().showAlertInformation(UtilitiesScreens.SELECTCUST);
        }
    }
    public void deleteWithOrders(){
        if(getPrincipalController().showAlertConfirmation(UtilitiesScreens.CONFDELETE)){//user confirms full deletion

            backup();
            Either<RestaurantError,Integer> confirmedDelete = customerServices.delete(deleteTable.getSelectionModel().getSelectedItem(), true);
            if(confirmedDelete.isRight()){ //full deletion works
                getPrincipalController().showAlertInformation(UtilitiesScreens.DELETEUSER);
                refresh();
            } else {
                getPrincipalController().showAlertError(confirmedDelete.getLeft().getMessage());
            }
        } else {//user cancels deletion
            getPrincipalController().showAlertInformation(UtilitiesScreens.CANCEL);
        }
    }
    public void backup(){
        Either<RestaurantError,Integer> backup = restaurantOrderServices.add(deleteTableOrder.getItems());
        if(backup.isRight()){ //full deletion works
            getPrincipalController().showAlertInformation(UtilitiesScreens.BACKUP_SUCCESSFUL);
        } else {
            getPrincipalController().showAlertError(backup.getLeft().getMessage());
        }
    }
    public void refresh(){
        deleteTable.getItems().clear();
        deleteTable.getItems().addAll(customerServices.getAll().getOrNull());
        deleteTableOrder.getItems().clear();
    }
}
