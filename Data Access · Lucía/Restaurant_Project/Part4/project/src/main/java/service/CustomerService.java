package service;

import dao.impl.CustomerDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credential;
import model.Customer;
import model.Order;

import java.util.List;

public class CustomerService {
    @Inject
    private CustomerDAOImpl customerDAO;
    @Inject
    private OrderService orderService;
    public boolean checkLogin(Credential c) {

        return customerDAO.checkLogin(c);
    }

    public void setUserConfirmedDeletion(boolean userConfirmedDeletion) {
        customerDAO.setUserConfirmedDeletion(userConfirmedDeletion);
    }

    public boolean isUserConfirmedDeletion() {
        return customerDAO.isUserConfirmedDeletion();
    }

    public Either<String, List<Customer>> getAll() {
        return customerDAO.getAll();
    }

    public Either<String, Customer> get(int id) {
        return customerDAO.get(id);
    }

    public int save(Customer c) {
        return customerDAO.save(c);
    }

    public int modify(Customer c, Customer modified) {
        return customerDAO.modify(c,modified);
    }

    public int delete(Customer c) {
        return customerDAO.delete(c);
    }


    public boolean hasAnyOrders(Customer c) {
        return customerDAO.hasOrders(c);
    }

}
