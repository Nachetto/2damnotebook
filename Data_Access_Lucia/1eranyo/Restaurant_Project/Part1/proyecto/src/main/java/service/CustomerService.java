package service;

import dao.impl.CustomerDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Credential;
import model.Customer;

import java.util.List;

public class CustomerService {
    @Inject
    private CustomerDAOImpl customerDAO;


    public boolean checkLogin(Credential c) {
        return customerDAO.checkLogin(c);
    }

    public Either<String, List<Customer>> getAll() {
        return customerDAO.getAll();
    }

    public Either<String, List<Customer>> get(int id) {
        return customerDAO.get(id);
    }

    public int save(Customer c) {
        return customerDAO.save(c);
    }

    public int modify(Customer c) {
        return customerDAO.modify(c);
    }

    public int delete(Customer c) {
        return customerDAO.delete(c);
    }


}
