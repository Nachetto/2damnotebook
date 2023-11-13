package dao;

import io.vavr.control.Either;
import model.Customer;

import java.util.List;

public interface CustomerDAO {
    Either<String, List<Customer>> getAll();

    int save(Customer c);

    int modify(Customer c, Customer cu);

    int delete(Customer c);
}
