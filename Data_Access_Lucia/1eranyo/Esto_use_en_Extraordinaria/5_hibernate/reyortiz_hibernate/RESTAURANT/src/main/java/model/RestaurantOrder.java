package model;


import dao.common.HqlQueries;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = UtilitiesModel.RESTAURANT_ORDER, schema = UtilitiesModel.REYMORTIZ_RESTAURANT)
@NamedQueries({@NamedQuery(name = HqlQueries.HQL_GET_ORDER_BY_CUSTOMER,
        query = HqlQueries.SELECT_O_FROM_RESTAURANT_ORDER_O_WHERE_O_CUSTOMER_IDCUSTOMER_IDCUSTOMER),
@NamedQuery(name = HqlQueries.HQL_DELETE_FROM_ORDERS_BY_CUSTOMER, query = HqlQueries.DELETE_FROM_RESTAURANT_ORDER_O_WHERE_O_CUSTOMER_ID_IDCUSTOMER)})
public class RestaurantOrder {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = UtilitiesModel.IDORDER, nullable = false)
    private int id;

    @Column(name = UtilitiesModel.TABLE_NUMBER, nullable = false)
    private int tableNumber;

    @ManyToOne
    @JoinColumn(name = UtilitiesModel.IDCUSTOMER, nullable = false)
    private Customer customer;

    @Column(name = UtilitiesModel.TIME_STAMP, nullable = true)
    private LocalDateTime date;

    @OneToMany(mappedBy = UtilitiesModel.ORDER, cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems;

    public RestaurantOrder(int tableNumber, Customer customer, LocalDateTime date, List<OrderItem> orderItems) {
        this.tableNumber = tableNumber;
        this.customer = customer;
        this.date = date;
        this.orderItems = orderItems;
    }

//    public RestaurantOrder(String line) {
//        String[] part = line.split(",");
//
//        this.id = Integer.parseInt(part[0]);
//        this.tableNumber = Integer.parseInt(part[1]);
//        this.customer = Integer.parseInt(part[2]);
//        date = LocalDateTime.parse(part[3]);
//    }

    public String stringToTextFile() {
        return id + "," + tableNumber + "," + customer.getIdcustomer() + "," + date;
    }
}
