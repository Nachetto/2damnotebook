package dao.impl.deprecated.xml;

import common.Configuration;
import common.RestaurantError;
import common.UtilitiesCommon;
import dao.RestaurantOrderDAO;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import model.OrderItem;
import model.RestaurantOrder;
import model.xml.OrderItemXML;
import model.xml.OrderXML;
import model.xml.OrdersXML;
import service.CustomerServices;
import service.OrderItemServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


//@Data
//@Log4j2
////implements RestaurantOrderDAO
//public class RestaurantOrderDaoImplXML  {
//    private final OrderItemServices orderItemServices;
//    private final CustomerServices customerServices;
//
//    @Inject
//    public RestaurantOrderDaoImplXML(OrderItemServices orderItemServices, CustomerServices customerServices) {
//        this.orderItemServices = orderItemServices;
//        this.customerServices = customerServices;
//    }
//
////    @Override
//    public Either<RestaurantError, List<RestaurantOrder>> getAll() {
//        Path file = Paths.get(Configuration.getInstance().getPropertyTXT(UtilitiesCommon.ORDERKEY));
//        List<RestaurantOrder> orders = new ArrayList<>();
//        BufferedReader reader;
//        String line;
//
//        try {
//            reader = Files.newBufferedReader(file);
//            while ((line = reader.readLine()) != null) {
//                //orders.add(new RestaurantOrder(line));
//            }
//            return Either.right(orders);
//        } catch (IOException e) {
//            log.error(UtilitiesCommon.GETERROR);
//            return Either.left(new RestaurantError(0, UtilitiesCommon.GETERROR));
//        }
//
//    }
//
////    @Override
//    public Either<RestaurantError, List<RestaurantOrder>> getAll(int id) {
//        return null;
//    }
//
//    private Either<RestaurantError, Integer> write(List<RestaurantOrder> restaurantOrders) {
//        Path file = Paths.get(Configuration.getInstance().getPropertyTXT(UtilitiesCommon.ORDERKEY));
//        boolean done = false;
//
//        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
//            for (RestaurantOrder restaurantOrder : restaurantOrders) {
//                writer.write(restaurantOrder.stringToTextFile());
//                writer.newLine();
//            }
//            done = true;
//        } catch (IOException e) {
//            log.error(UtilitiesCommon.WRITERROR);
//        }
//
//        if (done) {
//            return Either.right(restaurantOrders.size());
//        } else {
//            return Either.left(new RestaurantError(restaurantOrders.size(), UtilitiesCommon.WRITERROR));
//        }
//    }
//
////    @Override
//    public Either<RestaurantError, RestaurantOrder> get(int id) {
//        Either<RestaurantError, RestaurantOrder> either;
//        Either<RestaurantError, List<RestaurantOrder>> orders = getAll();
//        if (orders.isRight()) {
//            RestaurantOrder order = orders.get().stream().filter(i -> i.getId() == id).findFirst().orElse(null);
//            if (order != null) {
//                either = Either.right(order);
//            } else {
//                either = Either.left(new RestaurantError(1, UtilitiesCommon.ORDERERROR));
//            }
//        } else {
//            either = Either.left(orders.getLeft());
//        }
//        return either;
//    }
//
////    @Override
//    public Either<RestaurantError, Integer> add(RestaurantOrder restaurantOrder) {
//        List<RestaurantOrder> list = getAll().getOrNull();
//        list.add(restaurantOrder);
//
//        return write(list);
//    }
//
//    //XML BACKUP, divided in the methods add, convert and write for readability
////    @Override
//    public Either<RestaurantError, Integer> add(List<RestaurantOrder> orders) {
//        Either<RestaurantError, Integer> either;
//        Either<RestaurantError, Customer> getCustomer = customerServices.get(orders.get(0).getCustomer().getIdcustomer());
//
//        if (getCustomer.isRight()) {
//            String path = UtilitiesCommon.SRC_MAIN_RESOURCES_BACKUPS + getCustomer.get().getFirstName() + getCustomer.get().getLastName() + UtilitiesCommon.XML;
//
//            Either<RestaurantError, OrdersXML> ordersXML = convert(orders);
//
//            if (ordersXML.isRight()) {
//                Either<RestaurantError, Integer> write = write(ordersXML.get(), path);
//                if (write.isRight()) {
//                    either = Either.right(orders.size());
//                } else {
//                    either = Either.left(new RestaurantError(orders.size(), UtilitiesCommon.WRITERROR));
//                }
//            } else {
//                either = Either.left(ordersXML.getLeft());
//            }
//        } else {
//            either = Either.left(getCustomer.getLeft());
//        }
//
//        return either;
//    }
//
////    @Override
//    public Either<RestaurantError, Integer> update(RestaurantOrder restaurantOrder) {
//        Either<RestaurantError, Integer> update;
//        Either<RestaurantError, List<RestaurantOrder>> orders = getAll();
//        if (orders.isRight()) {
//            Either<RestaurantError, RestaurantOrder> oldOrder = get(restaurantOrder.getId());
//            if (oldOrder.isRight()) {
//                orders.get().remove(oldOrder.get());
//                orders.get().add(restaurantOrder);
//
//                Either<RestaurantError, Integer> write = write(orders.get());
//                if (write.isRight()) {
//                    update = Either.right(write.get());
//                } else {
//                    update = Either.left(write.getLeft());
//                }
//            } else {//customer not in list
//                update = Either.left(oldOrder.getLeft());
//            }
//        } else {
//            update = Either.left(orders.getLeft());
//        }
//        return update;
//    }
//
////    @Override
//    public Either<RestaurantError, Integer> delete(RestaurantOrder restaurantOrder, boolean confirm) {
//        return null;
//    }
//
//    //receives the model orders and returns an OrdersXML object for saving
//    private Either<RestaurantError, OrdersXML> convert(List<RestaurantOrder> orders) {
//        List<OrderXML> ordersXML = new ArrayList<>();
//        RestaurantError error = null;
//
//        for (RestaurantOrder order : orders) {
//            Either<RestaurantError, List<OrderItem>> orderItems = orderItemServices.getAll(order.getId());
//
//            if (orderItems.isRight()) {
//                List<OrderItemXML> itemsXML = new ArrayList<>();
//
//                for (OrderItem orderItem : orderItems.get()) {
//                    itemsXML.add(new OrderItemXML(orderItem.getMenuItem().getName(), orderItem.getQuantity()));
//                }
//                OrderXML orderXML = new OrderXML(order.getId(), itemsXML);
//                ordersXML.add(orderXML);
//            } else {
//                error = orderItems.getLeft();
//            }
//        }
//
//        return error == null ? Either.right(new OrdersXML(ordersXML)) : Either.left(error);
//    }
//
//    //for the actual writing on the file
//    private Either<RestaurantError, Integer> write(OrdersXML ordersXML, String path) {
//        Either<RestaurantError, Integer> either;
//        try {
//            JAXBContext context = JAXBContext.newInstance(OrdersXML.class);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            Path filePath = Paths.get(path);
//
//            marshaller.marshal(ordersXML, Files.newOutputStream(filePath));
//
//            either = Either.right(ordersXML.getList().size());
//        } catch (JAXBException | IOException e) {
//            either = Either.left(new RestaurantError(0, UtilitiesCommon.EMPTY));
//            log.error(e.getMessage());
//        }
//        return either;
//    }
//}
