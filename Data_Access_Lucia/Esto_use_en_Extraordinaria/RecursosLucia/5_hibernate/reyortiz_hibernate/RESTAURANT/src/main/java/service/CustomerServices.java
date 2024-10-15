package service;

import common.RestaurantError;
import common.UtilitiesCommon;
import dao.CustomerDAO;
import dao.impl.CustomerDAOImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Customer;

import java.util.Comparator;
import java.util.List;


public class CustomerServices {
    private final CustomerDAO customerDAO;

    @Inject
    public CustomerServices(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Either<RestaurantError, List<Customer>> getAll() {
        return customerDAO.getAll();
    }

    public Either<RestaurantError, Customer> get(int id) {
        if (getAll().isRight()) {
            return Either.right(customerDAO.get(id).get());
        } else {
            return Either.left(getAll().getLeft());
        }
    }

    public Either<RestaurantError, Integer> add(Customer customer) {
        return customerDAO.add(customer);
    }

    public Either<RestaurantError, Integer> update(Customer customer) {
        return customerDAO.update(customer);
    }

    public Either<RestaurantError, Integer> delete(Customer customer, boolean confirm) {
        if (getAll().getOrNull().stream().anyMatch(i -> i.equals(customer))) {
            return customerDAO.delete(customer, confirm);
        } else {
            return Either.left(new RestaurantError(1, UtilitiesCommon.DELETERROR));
        }
    }

    public Either<RestaurantError, Integer> generateId() {
        Either<RestaurantError, Integer> getId;
        Either<RestaurantError, List<Customer>> read = getAll();
        if (read.isRight()) {
            Customer maxCustomer = read.get().stream().max(Comparator.comparing(Customer::getIdcustomer)).orElse(null);
            if (maxCustomer != null) {
                getId = Either.right(maxCustomer.getIdcustomer() + 1);
            } else {
                getId = Either.right(1);
            }
        } else {
            getId = Either.left(read.getLeft());
        }
        return getId;
    }
}
