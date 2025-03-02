package dao.common;

public class HqlQueries {
    public static final String HQL_GET_ORDER_BY_CUSTOMER = "hql_get_order_by_customer";
    public static final String SELECT_O_FROM_RESTAURANT_ORDER_O_WHERE_O_CUSTOMER_IDCUSTOMER_IDCUSTOMER = "select o from RestaurantOrder o where o.customer.idcustomer = :idcustomer";
    public static final String IDCUSTOMER = "idcustomer";
    public static final String HQL_GET_ORDERITEMS_BY_ORDER = "hql_get_orderitems_by_order";
    public static final String SELECT_O_FROM_ORDER_ITEM_O_WHERE_O_ORDER_ID_IDORDER = "select o from OrderItem o where o.order.id = :idorder";
    public static final String HQL_DELETE_FROM_ORDERS_BY_CUSTOMER = "hql_delete_from_orders_by_customer";
    public static final String HQL_DELETE_ORDERITEMS_BY_CUSTOMER = "hql_delete_orderitems_by_customer";
    public static final String DELETE_FROM_RESTAURANT_ORDER_O_WHERE_O_CUSTOMER_ID_IDCUSTOMER = "DELETE FROM RestaurantOrder o WHERE o.customer.id = :idcustomer";
    public static final String DELETE_FROM_ORDER_ITEM_O_WHERE_O_ORDER_ID_IN_SELECT_ID_FROM_RESTAURANT_ORDER_R_WHERE_R_CUSTOMER_IDCUSTOMER_IDCUSTOMER = "DELETE FROM OrderItem o WHERE o.order.id in (select id from RestaurantOrder r where r.customer.idcustomer = :idcustomer)";
    public static final String FROM_CUSTOMER = "from Customer";
    public static final String IDORDER = "idorder";
    public static final String HQL_DELETE_ORDERITEMS_BY_ORDER = "hql_delete_orderitems_by_order";


    private HqlQueries(){}
}
