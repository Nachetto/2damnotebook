package dao.impl.deprecated.mappers;

//public class OrderMapper implements RowMapper<RestaurantOrder> {
//
//    @Override
//    public RestaurantOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
//        LocalDateTime date = LocalDateTime.ofInstant(rs.getTimestamp(SqlQueries.TIME_STAMP).toInstant(), ZoneId.systemDefault());
//        return new RestaurantOrder(rs.getInt(SqlQueries.IDORDER),
//                rs.getInt(SqlQueries.TABLE_NUMBER),
//                rs.getInt(SqlQueries.IDCUSTOMER),
//                date,
//                null);
//    }
//}
