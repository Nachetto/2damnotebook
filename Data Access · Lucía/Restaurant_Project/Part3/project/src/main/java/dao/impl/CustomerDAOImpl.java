package dao.impl;

import common.Constants;
import common.config.Configuration;
import dao.CustomerDAO;
import io.vavr.control.Either;
import model.Credential;
import model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerDAOImpl implements CustomerDAO {
    public Either<String, List<Customer>> getAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(Configuration.getInstance().getCustomerDataFile()));
            for (String line : lines.subList(1, lines.size())) {
                customers.add(new Customer(line));
            }
            return Either.right(customers);
        } catch (IOException | NumberFormatException e) {
            return Either.left(Constants.CUSTOMERDBERROR + e.getMessage());
        }
    }

    @Override
    public Either<String, Customer> get(int id) {
        List<Customer> list= getAll().get().stream().filter(c -> c.getId() == id).toList();
        if (1 != list.size()) {
            return Either.left(Constants.CUSTOMERDOESNTEXIST);
        } else {
            return Either.right(list.get(0));
        }
    }

    @Override
    public boolean checkLogin(Credential c) {
        Either<String, List<Customer>> result = getAll();

        if (result.isRight()) {
            List<Customer> customers = result.get();
            for (Customer cus : customers) {
                if (Objects.equals(cus.getCredential(), c)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int save(Customer c) {
        try {
            Files.write(Paths.get(Configuration.getInstance().getCustomerDataFile()), ('\n' + c.toStringTextFile()).getBytes(), StandardOpenOption.APPEND);
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public int modify(Customer initialcustomer, Customer modifiedcustomer) {
        delete(initialcustomer);
        return 1; // Indica éxito
    }

    @Override
    public int delete(Customer c) {
        if (c == null) {
            return -1;
        }
        try {
            List<Customer> customers = getAll().get();
            customers.remove(c);
            Files.write(Paths.get(Configuration.getInstance().getCustomerDataFile()), "id;first_name;last_name;email;phone;date_of_birth".getBytes());
            for (Customer customer : customers) {
                save(customer);
            }
            return 1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
