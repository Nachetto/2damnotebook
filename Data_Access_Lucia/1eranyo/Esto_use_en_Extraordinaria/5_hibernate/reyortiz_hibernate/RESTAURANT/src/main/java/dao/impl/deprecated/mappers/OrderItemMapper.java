package dao.impl.deprecated.mappers;

//public class OrderItemMapper implements RowMapper<OrderItem> {
//    @Override
//    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
//        MenuItem menuItem = new MenuItem(rs.getInt(SqlQueries.IDMENU_ITEM),
//                rs.getString(SqlQueries.NAME),
//                rs.getString(SqlQueries.DESCRIPTION),
//                rs.getDouble(SqlQueries.PRICE));
//
//        return new OrderItem(rs.getInt(SqlQueries.IDORDER_ITEM),
//                rs.getInt(SqlQueries.IDORDER),
//                menuItem,
//                rs.getInt(SqlQueries.QUANTITY));
//    }
//}
