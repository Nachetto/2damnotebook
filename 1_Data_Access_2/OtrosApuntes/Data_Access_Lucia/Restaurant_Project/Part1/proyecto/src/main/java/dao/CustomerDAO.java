package dao;

import io.vavr.control.Either;
import model.Credential;
import model.Customer;

import java.util.List;

public interface CustomerDAO {
    Either<String, List<Customer>> getAll();

    Either<String, List<Customer>> get(int id);

    int save(Customer c);

    int modify(Customer c);

    int delete(Customer c);

    boolean checkLogin(Credential c);
}
