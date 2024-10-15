package dao.impl.deprecated.txt;

import common.Configuration;
import common.RestaurantError;
import common.UtilitiesCommon;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import model.Customer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.vavr.control.Either;

//@Data
//@Log4j2
////add implements CustomerDAO and override for it to work
//public class CustomerDAOImplTxt {
//    //@Override
//    public Either<RestaurantError, List<Customer>> getAll() {
//        Either<RestaurantError, List<Customer>> either;
//
//        Path file = Paths.get(Configuration.getInstance().getPropertyTXT(UtilitiesCommon.CUSTOMERKEY));
//        List<String> lines;
//        List<Customer> customers = new ArrayList<>();
//
//        try {
//            lines = Files.readAllLines(file);
//            for (String line : lines) {
//                customers.add(new Customer(line));
//            }
//            either = Either.right(customers);
//        } catch (IOException e) {
//            log.error(UtilitiesCommon.GETERROR);
//            either = Either.left(new RestaurantError(0, UtilitiesCommon.GETERROR));
//        }
//        return either;
//    }
//
//
//    private Either<RestaurantError, Integer> write(List<Customer> customers) {
//        Either<RestaurantError, Integer> either;
//
//        Path file = Paths.get(Configuration.getInstance().getPropertyTXT(UtilitiesCommon.CUSTOMERKEY));
//        List<String> list = new ArrayList<>();
//        boolean done = false;
//        for (Customer customer : customers) {
//            list.add(customer.stringToTextFile());
//        }
//
//        try {
//            Files.write(file, list);
//            done = true;
//        } catch (IOException e) {
//            log.error(UtilitiesCommon.WRITERROR);
//        }
//
//        if (done) {
//            either = Either.right(list.size());
//        } else {
//            either = Either.left(new RestaurantError(list.size(), UtilitiesCommon.WRITERROR));
//        }
//        return either;
//    }
//
//    //@Override
//    public Either<RestaurantError, Customer> get(int id) {
//        Either<RestaurantError, Customer> either;
//        Either<RestaurantError, List<Customer>> customers = getAll();
//        if (customers.isRight()) {
//            Customer customer = customers.get().stream().filter(i -> i.getIdcustomer() == id).findFirst().orElse(null);
//            if (customer != null) {
//                either = Either.right(customer);
//            } else {
//                either = Either.left(new RestaurantError(1, UtilitiesCommon.CUSTERROR));
//            }
//        } else {
//            either = Either.left(customers.getLeft());
//        }
//        return either;
//    }
//
//    //@Override
//    public Either<RestaurantError, Integer> add(Customer customer) {
//        Either<RestaurantError, Integer> add;
//        Either<RestaurantError, List<Customer>> customers = getAll();
//        if (customers.isRight()) {
//            customers.get().add(customer);
//
//            Either<RestaurantError, Integer> write = write(customers.get());
//            if (write.isRight()) {
//                add = Either.right(write.get());
//            } else {
//                add = Either.left(write.getLeft());
//            }
//        } else {
//            add = Either.left(customers.getLeft());
//        }
//        return add;
//    }
//
//    //@Override
//    public Either<RestaurantError, Integer> update(Customer customer) {
//        Either<RestaurantError, Integer> update;
//        Either<RestaurantError, List<Customer>> customers = getAll();
//        if (customers.isRight()) {
//            Either<RestaurantError, Customer> oldCustomer = get(customer.getIdcustomer());
//            if (oldCustomer.isRight()) {
//                customers.get().remove(oldCustomer.get());
//                customers.get().add(customer);
//
//                Either<RestaurantError, Integer> write = write(customers.get());
//                if (write.isRight()) {
//                    update = Either.right(write.get());
//                } else {
//                    update = Either.left(write.getLeft());
//                }
//            } else {//customer not in list
//                update = Either.left(oldCustomer.getLeft());
//            }
//        } else {//failure to read list
//            update = Either.left(customers.getLeft());
//        }
//        return update;
//    }
//
//    //@Override
//    public Either<RestaurantError, Integer> delete(Customer customer) {
//        Either<RestaurantError, Integer> delete;
//        Either<RestaurantError, List<Customer>> customers = getAll();
//        if (customers.isRight()) {
//            customers.get().remove(customer);
//
//            Either<RestaurantError, Integer> write = write(customers.get());
//            if (write.isRight()) {
//                delete = Either.right(write.get());
//            } else {
//                delete = Either.left(write.getLeft());
//            }
//        } else {
//            delete = Either.left(customers.getLeft());
//        }
//
//        return delete;
//    }
//}
