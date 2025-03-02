package model;

import dao.common.HqlQueries;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = UtilitiesModel.ORDER_ITEM, schema = UtilitiesModel.REYMORTIZ_RESTAURANT)
@NamedQueries({@NamedQuery(name = HqlQueries.HQL_GET_ORDERITEMS_BY_ORDER,
        query = HqlQueries.SELECT_O_FROM_ORDER_ITEM_O_WHERE_O_ORDER_ID_IDORDER)
        ,@NamedQuery(name = HqlQueries.HQL_DELETE_ORDERITEMS_BY_ORDER, query = "DELETE FROM OrderItem o WHERE o.order.id = :idorder")
,@NamedQuery(name = HqlQueries.HQL_DELETE_ORDERITEMS_BY_CUSTOMER, query = HqlQueries.DELETE_FROM_ORDER_ITEM_O_WHERE_O_ORDER_ID_IN_SELECT_ID_FROM_RESTAURANT_ORDER_R_WHERE_R_CUSTOMER_IDCUSTOMER_IDCUSTOMER)})

public class OrderItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = UtilitiesModel.IDORDER_ITEM, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = UtilitiesModel.IDORDER, referencedColumnName = UtilitiesModel.IDORDER, nullable = false)
    private RestaurantOrder order;

    @ManyToOne
    @JoinColumn(name = UtilitiesModel.IDMENU_ITEM, referencedColumnName = UtilitiesModel.IDMENU_ITEM,nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false)
    private int quantity;

    public OrderItem(int id, MenuItem menuItem, int quantity) {
        this.id = id;
        this.menuItem = menuItem;
        this.quantity = quantity;
    }
}
