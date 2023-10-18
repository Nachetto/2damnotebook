package dao.impl;

import common.Constants;
import common.config.Configuration;
import dao.CustomerDAO;
import dao.common.SQLConstants;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import model.Credential;
import model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.*;
@Log4j2
public class CustomerDAOImpl implements CustomerDAO {

    public Either<String, List<Customer>> getAll() {
        try(Connection myConnection=DriverManager.getConnection("jdbc:mysql://dam2.mysql.iesquevedo.es: 3335/ignacioLlorente_restaurant","root","quevedo2dam");
            Statement stmt= myConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_customers_QUERY);
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                Customer resultCustomer = new Customer(
                        rs.getInt("id"),
                        rs.getInt("phone"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        new Credential("root", "2dam"),
                        rs.getDate("date_of_birth").toLocalDate()
                );
                customers.add(resultCustomer);
            }
            return Either.right(customers);
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            return Either.left(Constants.CUSTOMERDBERROR + e.getMessage());
        }
    }


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
        return 1;
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
