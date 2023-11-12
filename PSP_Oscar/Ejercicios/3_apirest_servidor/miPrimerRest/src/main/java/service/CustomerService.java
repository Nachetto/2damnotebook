package service;

import dao.impl.CustomerDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDAOImpl customerDAO;
    @Inject
    public CustomerService(CustomerDAOImpl customerDAO) {
        this.customerDAO = customerDAO;
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

    public boolean hasAnyOrders(int i) {
        return customerDAO.hasOrders(get(i).get());
    }
}
