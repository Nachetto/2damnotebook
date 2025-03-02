package dao.impl.deprecated.jdbc;

import dao.OrderItemDAO;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

//@Log4j2
//@Data
//public class OrderItemDAOImplJDBC implements OrderItemDAO {
//    private DBConnectionPool dbConnection;
//    private MenuItemServices menuItemServices;
//
//    @Inject
//    public OrderItemDAOImplJDBC(DBConnectionPool dbConnection, MenuItemServices menuItemServices) {
//        this.dbConnection = dbConnection;
//        this.menuItemServices = menuItemServices;
//    }
//
//    @Override
//    public Either<RestaurantError,List<OrderItem>> getAll(int orderId){
//        Either<RestaurantError,List<OrderItem>> either;
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
//        List<OrderItem> orderItems = jdbcTemplate.query(SqlQueries.ORDERITEMGETWITHMENUITEM, new OrderItemMapper(), orderId);
//        either = orderItems.isEmpty() ? Either.left(new RestaurantError(0, UtilitiesCommon.THERE_ARE_NO_ITEMS_IN_THIS_ORDER)) : Either.right(orderItems);
//        return either;
//    }
//
////    JDBC METHOD
////    @Override
////    public Either<RestaurantError, List<OrderItem>> getAll(int orderId) {
////        Either<RestaurantError, List<OrderItem>> either;
////        try (Connection connection = dbConnection.getConnection();
////             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ORDERITEMGETBYORDER)) {
////
////            preparedStatement.setInt(1, orderId);
////            ResultSet resultSet = preparedStatement.executeQuery();
////            Either<RestaurantError, List<OrderItem>> read = readRS(resultSet);
////
////            if (read.isRight()) {
////                either = Either.right(read.get());
////            } else {
////                either = Either.left(read.getLeft());
////            }
////
////        } catch (SQLException e) {
////            either = Either.left(new RestaurantError(0, e.getMessage()));
////        }
////        return either;
////    }
//
//
//    @Override
//    public Either<RestaurantError, Integer> update(OrderItem orderItem) {
//        Either<RestaurantError, Integer> either;
//        try (Connection connection = dbConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ORDERITEMUPDATE)) {
//
//            preparedStatement.setInt(1, orderItem.getOrder());
//            preparedStatement.setInt(2, orderItem.getMenuItem().getId());
//            preparedStatement.setInt(3, orderItem.getQuantity());
//
//            int result = preparedStatement.executeUpdate();
//
//            if (result > 0) {
//                either = Either.right(result);
//            } else {
//                either = Either.left(new RestaurantError(result, UtilitiesCommon.UPDATERROR));
//            }
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, Integer> add(OrderItem orderItem) {
//        return null;
//    }
//
//    @Override
//    public Either<RestaurantError, Integer> add(List<OrderItem> orderItems) {
//        Either<RestaurantError, Integer> either;
//        try (Connection connection = dbConnection.getConnection()) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ORDERITEMADD, Statement.RETURN_GENERATED_KEYS)) {
//                connection.setAutoCommit(false);
//                int rows = 0;
//                for (OrderItem orderItem : orderItems) {
//                    preparedStatement.setInt(1, orderItem.getOrder());
//                    preparedStatement.setInt(2, orderItem.getMenuItem().getId());
//                    preparedStatement.setInt(3, orderItem.getQuantity());
//
//                    int result = preparedStatement.executeUpdate();
//                    ResultSet rs = preparedStatement.getGeneratedKeys();
//                    if (rs.next()) {
//                        int autoId = rs.getInt(1);
//                        orderItem.setId(autoId);
//                    }
//                    rows += result;
//                }
//                either = Either.right(rows);
//
//            } catch (SQLException e) {
//                either = Either.left(new RestaurantError(0, e.getMessage()));
//                try {
//                    connection.rollback();
//                } catch (SQLException ex) {
//                    either = Either.left(new RestaurantError(0, ex.getMessage()));
//                }
//            } finally {
//                connection.setAutoCommit(true);
//            }
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, Integer> delete(OrderItem orderItem) {
//        Either<RestaurantError, Integer> either;
//        try(Connection connection =  dbConnection.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ORDERITEMDELETEBYORDER)){
//            preparedStatement.setInt(1,orderItem.getOrder());
//            int result = preparedStatement.executeUpdate();
//            either = Either.right(result);
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0,e.getMessage()));
//        }
//        return either;
//    }
//
//    private Either<RestaurantError, List<OrderItem>> readRS(ResultSet resultSet) {
//        Either<RestaurantError, List<OrderItem>> either;
//        List<OrderItem> list = new ArrayList<>();
//        try {
//            while (resultSet.next()) {
//                int id = resultSet.getInt(SqlQueries.IDORDER_ITEM);
//                int idorder = resultSet.getInt(SqlQueries.IDORDER);
//                MenuItem menuItem = menuItemServices.get(resultSet.getInt(SqlQueries.IDMENU_ITEM)).get();
//                int quantity = resultSet.getInt(SqlQueries.QUANTITY);
//                list.add(new OrderItem(id, idorder, menuItem, quantity));
//            }
//            either = Either.right(list);
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//        return either;
//    }

//}
