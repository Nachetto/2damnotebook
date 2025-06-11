package dao.impl.deprecated.xml;

import common.Configuration;
import common.RestaurantError;
import common.UtilitiesCommon;
import io.vavr.control.Either;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import model.MenuItem;
import model.OrderItem;
import model.RestaurantOrder;
import model.xml.OrderItemXML;
import model.xml.OrderXML;
import model.xml.OrdersXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//@Log4j2
//@Data
//public class OrderItemDAOImplXML {
////    @Override
//    public Either<RestaurantError, List<OrderItem>> getAll(RestaurantOrder restaurantOrder) {
//        Either<RestaurantError, List<OrderItem>> either;
//        List<OrderItem> list = new ArrayList<>();
//
//        Either<RestaurantError, OrdersXML> listXml = read();
//        if (listXml.isRight()) {
//            OrderXML order = listXml.get().getList().stream().filter(i -> i.getId() == restaurantOrder.getId()).findFirst().orElse(null);
//
//            if (order != null) {
//                for (OrderItemXML orderItemXML : order.getOrderItems()) {
//                    list.add(new OrderItem(0, restaurantOrder, new MenuItem(0,orderItemXML.getMenuItem(),"",0), orderItemXML.getQuantity()));
//                }
//                either = Either.right(list);
//            } else {
//                either = Either.left(new RestaurantError(1, UtilitiesCommon.ORDERERROR));
//            }
//
//        } else {
//            either = Either.left(listXml.getLeft());
//        }
//        return either;
//    }
//
//
////    @Override
//    public Either<RestaurantError, Integer> add(List<OrderItem> orderItems, int orderId) {
//        Either<RestaurantError, Integer> add;
//        Either<RestaurantError, OrdersXML> read = read();
//        if (read.isRight()) {
//            //add order
//            List<OrderItemXML> itemsXML = new ArrayList<>();
//            for (OrderItem orderItem : orderItems) {
//                itemsXML.add(new OrderItemXML(orderItem.getMenuItem().getName(), orderItem.getQuantity()));
//            }
//            OrderXML orderXML = new OrderXML(orderId, itemsXML);
//            List<OrderXML> orders = read.get().getList();
//            orders.add(orderXML);
//
//            //write order
//            Either<RestaurantError, Integer> write = write(new OrdersXML(orders));
//            if (write.isRight()) {
//                add = Either.right(orders.size());
//            } else {
//                add = Either.left(write.getLeft());
//            }
//        } else {
//            add = Either.left(read.getLeft());
//        }
//        return add;
//    }
//
////    @Override
////    public Either<RestaurantError, Integer> update(List<OrderItem> orderItems, int orderId) {
////        Either<RestaurantError, Integer> update;
////        Either<RestaurantError, OrdersXML> read = read();
////        if (read.isRight()) {
////            Either<RestaurantError, List<OrderItem>> oldItems = getAll(orderId);
////            List<OrderXML> orders = read.get().getList();
////            if (oldItems.isRight()) {
////                //delete old order
////                List<OrderItemXML> oldItemsXML = new ArrayList<>();
////                for (OrderItem orderItem : oldItems.get()) {
////                    oldItemsXML.add(new OrderItemXML(orderItem.getMenuItem().getName(), orderItem.getQuantity()));
////                }
////                OrderXML oldOrder = new OrderXML(orderId, oldItemsXML);
////                orders.remove(oldOrder);
////
////                //add updated order
////                List<OrderItemXML> newItemsXML = new ArrayList<>();
////                for (OrderItem orderItem : orderItems) {
////                    newItemsXML.add(new OrderItemXML(orderItem.getMenuItem().getName(), orderItem.getQuantity()));
////                }
////                OrderXML newOrder = new OrderXML(orderId, newItemsXML);
////                orders.add(newOrder);
////
////                //write
////                Either<RestaurantError, Integer> write = write(new OrdersXML(orders));
////                if (write.isRight()) {
////                    update = Either.right(write.get());
////                } else {
////                    update = Either.left(write.getLeft());
////                }
////            } else {//order not in list
////                update = Either.left(oldItems.getLeft());
////            }
////        } else {//failure to read orders
////            update = Either.left(read.getLeft());
////        }
////        return update;
////    }
////
//////    @Override
////    public Either<RestaurantError, Integer> delete(int orderId) {
////        Either<RestaurantError, Integer> delete;
////        Either<RestaurantError, OrdersXML> read = read();
////        if (read.isRight()) {
////            List<OrderXML> orders = read.get().getList();
////            Either<RestaurantError, List<OrderItem>> deleteList = getAll(orderId);
////
////            if(deleteList.isRight()){
////                List<OrderItemXML> deleteItemsXML = new ArrayList<>();
////                for(OrderItem orderItem : deleteList.get()){
////                    deleteItemsXML.add(new OrderItemXML(orderItem.getMenuItem().getName(),orderItem.getQuantity()));
////                }
////                OrderXML deleteOrder = new OrderXML(orderId, deleteItemsXML);
////                orders.remove(deleteOrder);
////
////                //write
////                Either<RestaurantError, Integer> write = write(new OrdersXML(orders));
////                if (write.isRight()) {
////                    delete = Either.right(orders.size());
////                } else {
////                    delete = Either.left(write.getLeft());
////                }
////            } else {
////                delete = Either.left(deleteList.getLeft());
////            }
////        } else {
////            delete = Either.left(read.getLeft());
////        }
////        return delete;
////    }
//
//    private Either<RestaurantError, OrdersXML> read() {
//        Either<RestaurantError, OrdersXML> either;
//        try {
//            JAXBContext context = JAXBContext.newInstance(OrdersXML.class);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//
//            Path xmlFile = Paths.get(Configuration.getInstance().getPropertyXML(UtilitiesCommon.ORDERXMLKEY));
//
//            OrdersXML listXml = (OrdersXML) unmarshaller.unmarshal(Files.newInputStream(xmlFile));
//
//            either = Either.right(listXml);
//
//        } catch (JAXBException | IOException e) {
//            either = Either.left(new RestaurantError(0, UtilitiesCommon.EMPTY));
//            log.error(e.getMessage());
//        }
//        return either;
//    }
//
//    private Either<RestaurantError, Integer> write(OrdersXML ordersXML) {
//        Either<RestaurantError, Integer> either;
//        try {
//            JAXBContext context = JAXBContext.newInstance(OrdersXML.class);
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            Path xmlFile = Paths.get(Configuration.getInstance().getPropertyXML(UtilitiesCommon.ORDERXMLKEY));
//
//            marshaller.marshal(ordersXML, Files.newOutputStream(xmlFile));
//
//            either = Either.right(ordersXML.getList().size());
//        } catch (JAXBException | IOException e) {
//            either = Either.left(new RestaurantError(0, UtilitiesCommon.EMPTY));
//            log.error(e.getMessage());
//        }
//        return either;
//    }
//}
