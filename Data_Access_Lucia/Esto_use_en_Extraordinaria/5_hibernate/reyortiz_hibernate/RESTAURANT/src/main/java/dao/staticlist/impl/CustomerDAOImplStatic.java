package dao.staticlist.impl;


import common.RestaurantError;
import common.UtilitiesCommon;
import dao.staticlist.CustomerDAOStatic;
import io.vavr.control.Either;
import lombok.Data;
import model.Customer;
import java.util.List;
@Data
public class CustomerDAOImplStatic implements CustomerDAOStatic {
    private final List<Customer> customers;

    public CustomerDAOImplStatic() {
        customers = List.of(
                //new Customer(1, "Nora", "Durst", "durst@gmail.com", "666555777",),
                //new Customer(2, "Kevin", "Garvey", "kevgar@gmail.com", "654321123"),
                //new Customer(3, "Matt", "Jamison", "revmatt@gmail.com", "765765765"),
                //new Customer(4, "Chuck", "Bass", "chuuuck@gmail.com", "723456345")
        );
    }

    @Override
    public Either<RestaurantError, List<Customer>> getAll() {
        if (customers == null) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.EMPTY));
        } else {
            return Either.right(customers);
        }
    }

    @Override
    public Either<RestaurantError, Customer> get(int id) {
        if (id == 0) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.GETERROR));
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<RestaurantError, Integer> add(Customer customer) {
        if (customer.getIdcustomer() == 0) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.ADDERROR));
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<RestaurantError, Integer> update(Customer customer) {
        if (customer.getIdcustomer() == 0) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.UPDATERROR));
        } else {
            return Either.right(null);
        }
    }

    @Override
    public Either<RestaurantError, Integer> delete(Customer customer) {
        if (customer.getIdcustomer() == 0) {
            return Either.left(new RestaurantError(0, UtilitiesCommon.DELETERROR));
        } else {
            return Either.right(null);
        }
    }
}
