package dao.impl;

import dao.CustomerDAO;
import io.vavr.control.Either;
import model.Credential;
import model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public Either<String, List<Customer>> getAll() {
        Either<String, List<Customer>> result;
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, 666666666, "Administrator", "Boss", "admin@admin.com", new Credential("root", "2dam"), LocalDate.of(1999, 10, 1)));
        customers.add(new Customer(2, 777777777, "Charles", "johnson", "charles@johnson.com", new Credential("charles", "1234"), LocalDate.of(2001, 11, 28)));
        customers.add(new Customer(3, 888888888, "Lucy", "dataaccess", "lucy@dataaccess.com", new Credential("lucy", "1234"), LocalDate.of(2000, 12, 2)));
        result = Either.right(customers);
        return result;
    }

    @Override
    public Either<String, List<Customer>> get(int id) {
        return Either.right(getAll().get().stream().filter(c -> c.getId() == id).toList());
    }

    @Override
    public boolean checkLogin(Credential c) {
        boolean res = false;
        for (Customer cus : getAll().get()) {
            if (Objects.equals(cus.getCredential(), c)) {
                res = true;
                break;
            }
        }
        return res;
    }

    @Override
    public int save(Customer c) {
        return 0;
    }

    @Override
    public int modify(Customer c) {
        return 0;
    }

    @Override
    public int delete(Customer c) {
        return 0;
    }
}
