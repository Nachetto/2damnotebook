package dao.impl.deprecated.spring;

import dao.RestaurantOrderDAO;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

//
//@Data
//@Log4j2
//public class RestaurantOrderDAOImplSpring implements RestaurantOrderDAO {

//    private final DBConnectionPool dbConnection;
//    private final OrderItemServices orderItemServices;
//    private final CustomerServices customerServices;
//
//    @Inject
//    public RestaurantOrderDAOImplSpring(DBConnectionPool dbConnection, OrderItemServices orderItemServices, CustomerServices customerServices) {
//        this.dbConnection = dbConnection;
//        this.orderItemServices = orderItemServices;
//        this.customerServices = customerServices;
//    }
//
//
//    @Override
//    public Either<RestaurantError, List<RestaurantOrder>> getAll() {
//        Either<RestaurantError, List<RestaurantOrder>> either;
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
//        List<RestaurantOrder> orders = jdbcTemplate.query(SqlQueries.ORDERGETALL, new OrderMapper());
//        either = orders.isEmpty() ? Either.left(new RestaurantError(0, UtilitiesCommon.THIS_CUSTOMER_HAS_NO_ORDERS)) : Either.right(orders);
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, List<RestaurantOrder>> getAll(int id) {
//        Either<RestaurantError, List<RestaurantOrder>> either;
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
//        List<RestaurantOrder> orders = jdbcTemplate.query(SqlQueries.ORDERGETBYCUSTOMER, new OrderMapper(), id);
//        either = orders.isEmpty() ? Either.left(new RestaurantError(0, UtilitiesCommon.THIS_CUSTOMER_HAS_NO_ORDERS)) : Either.right(orders);
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, RestaurantOrder> get(int id) {
//        Either<RestaurantError, RestaurantOrder> either;
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConnection.getDataSource());
//        List<RestaurantOrder> orders = jdbcTemplate.query(SqlQueries.ORDERGET, new OrderMapper(), id);
//        either = orders.isEmpty() ? Either.left(new RestaurantError(0, UtilitiesCommon.GETERROR)) : Either.right(orders.get(0));
//        return either;
//    }
//
//
//    //    JDBC METHODS
////    @Override
////    public Either<RestaurantError, List<RestaurantOrder>> getAll() {
////        Either<RestaurantError, List<RestaurantOrder>> either;
////
////        try (Connection connection = dbConnection.getConnection();
////             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
////            ResultSet resultSet = statement.executeQuery(SqlQueries.ORDERGETALL);
////            Either<RestaurantError, List<RestaurantOrder>> read = readRS(resultSet);
////            if (read.isRight()) {
////                either = Either.right(read.get());
////            } else {
////                either = Either.left(read.getLeft());
////            }
////
////        } catch (SQLException e) {
////            either = Either.left(new RestaurantError(0, e.getMessage()));
////        }
////
////        return either;
////    }
////
////    @Override
////    public Either<RestaurantError, List<RestaurantOrder>> getAll(int id) {
////        Either<RestaurantError, List<RestaurantOrder>> either;
////        try (Connection connection = dbConnection.getConnection();
////             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ORDERGETBYCUSTOMER)) {
////
////            preparedStatement.setInt(1, id);
////            ResultSet resultSet = preparedStatement.executeQuery();
////
////            Either<RestaurantError, List<RestaurantOrder>> read = readRS(resultSet);
////            if (read.isRight()) {
////                either = Either.right(read.get());
////            } else {
////                either = Either.left(read.getLeft());
////            }
////        } catch (SQLException e) {
////            either = Either.left(new RestaurantError(0, e.getMessage()));
////        }
////
////        return either;
////    }
////
////    @Override
////    public Either<RestaurantError, RestaurantOrder> get(int id) {
////        Either<RestaurantError, RestaurantOrder> either;
////        try (Connection connection = dbConnection.getConnection();
////             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ORDERGET)) {
////
////            preparedStatement.setInt(1, id);
////            ResultSet resultSet = preparedStatement.executeQuery();
////
////            Either<RestaurantError, List<RestaurantOrder>> read = readRS(resultSet);
////            if (read.isRight()) {
////                either = Either.right(read.get().get(0));
////            } else {
////                either = Either.left(read.getLeft());
////            }
////        } catch (SQLException e) {
////            either = Either.left(new RestaurantError(0, e.getMessage()));
////        }
////
////        return either;
////    }
//    @Override
//    public Either<RestaurantError, Integer> add(RestaurantOrder restaurantOrder) {
//        Either<RestaurantError, Integer> either;
//
//        try (Connection connection = dbConnection.getConnection()) {
//            try (PreparedStatement preparedStatementOrder = connection.prepareStatement(SqlQueries.ORDERADD, Statement.RETURN_GENERATED_KEYS)) {
//                preparedStatementOrder.setInt(1, restaurantOrder.getTableNumber());
//                preparedStatementOrder.setInt(2, restaurantOrder.getCustomer());
//                preparedStatementOrder.setTimestamp(3, Timestamp.valueOf(restaurantOrder.getDate()));
//
//                connection.setAutoCommit(false);
//                RestaurantError error = null;
//
//                int result = preparedStatementOrder.executeUpdate();
//                if (result > 0) {
//                    ResultSet rs = preparedStatementOrder.getGeneratedKeys();
//                    if (rs.next()) {
//                        int autoId = rs.getInt(1);
//                        restaurantOrder.setId(autoId);
//                        for (OrderItem orderItem : restaurantOrder.getOrderItems()) {
//                            orderItem.setOrder(autoId);
//                        }
//                    }
//                }
//                for (OrderItem orderItem : restaurantOrder.getOrderItems()) {
//                    Either<RestaurantError, Integer> add = addItems(orderItem, connection);
//                    if (add.isLeft()) {
//                        error = add.getLeft();
//                    }
//                }
//
//                connection.commit();
//
//                if (result > 0 && error == null) {
//                    either = Either.right(result);
//                } else {
//                    either = Either.left(new RestaurantError(result, UtilitiesCommon.ADDERROR));
//                }
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
//
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, Integer> add(List<RestaurantOrder> orders) {
//        //Backup is in RestaurantOrderDaoImplXML
//        return null;
//    }
//
//
//    private Either<RestaurantError, Integer> addItems(OrderItem orderItem, Connection connection) {
//        Either<RestaurantError, Integer> either;
//        try (PreparedStatement preparedStatementItem = connection.prepareStatement(SqlQueries.ORDERITEMADD, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatementItem.setInt(1, orderItem.getOrder());
//            preparedStatementItem.setInt(2, orderItem.getMenuItem().getId());
//            preparedStatementItem.setInt(3, orderItem.getQuantity());
//
//            int result = preparedStatementItem.executeUpdate();
//
//            if (result > 0) {
//                either = Either.right(result);
//                ResultSet rs = preparedStatementItem.getGeneratedKeys();
//                if (rs.next()) {
//                    int autoId = rs.getInt(1);
//                    orderItem.setId(autoId);
//                }
//            } else {
//                either = Either.left(new RestaurantError(result, UtilitiesCommon.UPDATERROR));
//            }
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, Integer> update(RestaurantOrder restaurantOrder) {
//        Either<RestaurantError, Integer> either;
//
//        try (Connection connection = dbConnection.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SqlQueries.ORDERUPDATE)) {
//
//            preparedStatement.setInt(1, restaurantOrder.getTableNumber());
//            preparedStatement.setInt(2, restaurantOrder.getCustomer());
//            preparedStatement.setInt(3, restaurantOrder.getId());
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
//
//        return either;
//    }
//
//    @Override
//    public Either<RestaurantError, Integer> delete(RestaurantOrder restaurantOrder, boolean confirm) {
//        Either<RestaurantError, Integer> either;
//        try (Connection connection = dbConnection.getConnection()) {
//            if (confirm) {
//                either = deleteWithItems(restaurantOrder, connection);
//            } else {
//                either = deleteWithoutItems(restaurantOrder, connection);
//            }
//            connection.setAutoCommit(true);
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//
//        return either;
//    }
//
//    private Either<RestaurantError, Integer> deleteWithoutItems(RestaurantOrder restaurantOrder, Connection connection) {
//        Either<RestaurantError, Integer> either;
//
//        try (PreparedStatement preparedStatementOrder = connection.prepareStatement(SqlQueries.ORDERDELETE)) {
//            preparedStatementOrder.setInt(1, restaurantOrder.getId());
//
//            connection.setAutoCommit(false);
//            int result = preparedStatementOrder.executeUpdate();
//
//            connection.commit();
//            either = Either.right(result);
//
//        } catch (SQLException e) {
//            if (e.getErrorCode() == 1451) {
//                either = Either.left(new RestaurantError(0, UtilitiesCommon.CONFDELETE));
//            } else {
//                either = Either.left(new RestaurantError(0, e.getMessage()));
//            }
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                either = Either.left(new RestaurantError(0, ex.getMessage()));
//            }
//        }
//        return either;
//    }
//
//    private Either<RestaurantError, Integer> deleteWithItems(RestaurantOrder restaurantOrder, Connection connection) {
//        Either<RestaurantError, Integer> either;
//
//        try (PreparedStatement preparedStatementItem = connection.prepareStatement(SqlQueries.ORDERITEMDELETEBYORDER);
//             PreparedStatement preparedStatementOrder = connection.prepareStatement(SqlQueries.ORDERDELETE)) {
//
//            preparedStatementItem.setInt(1, restaurantOrder.getId());
//            preparedStatementOrder.setInt(1, restaurantOrder.getId());
//
//            connection.setAutoCommit(false);
//            preparedStatementItem.executeUpdate();
//            int result = preparedStatementOrder.executeUpdate();
//
//            connection.commit();
//            either = Either.right(result);
//
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                either = Either.left(new RestaurantError(0, ex.getMessage()));
//            }
//        }
//        return either;
//    }
//
//    private Either<RestaurantError, List<RestaurantOrder>> readRS(ResultSet resultSet) {
//        Either<RestaurantError, List<RestaurantOrder>> either;
//        List<RestaurantOrder> list = new ArrayList<>();
//        try {
//            while (resultSet.next()) {
//                int id = resultSet.getInt(SqlQueries.IDORDER);
//                int tableNumber = resultSet.getInt(SqlQueries.TABLE_NUMBER);
//                int idcustomer = resultSet.getInt(SqlQueries.IDCUSTOMER);
//                LocalDateTime date = LocalDateTime.ofInstant(resultSet.getTimestamp(SqlQueries.TIME_STAMP).toInstant(), ZoneId.systemDefault());
//
//                Either<RestaurantError, List<OrderItem>> orderItems = orderItemServices.getAll(id);
//                if (orderItems.isRight()) {
//                    list.add(new RestaurantOrder(id, tableNumber, idcustomer, date, orderItems.get()));
//                } else {
//                    throw new SQLException(orderItems.getLeft().getMessage());
//                }
//            }
//            either = Either.right(list);
//        } catch (SQLException e) {
//            either = Either.left(new RestaurantError(0, e.getMessage()));
//        }
//        return either;
//    }
//}
