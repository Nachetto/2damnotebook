package model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
@ToString

@Entity
@Table(name = "coffees")

@NamedQueries({@NamedQuery(name = "hql_get_coffee_by_name",
        query = "from Coffee where cofName= :name")})

public class Coffee {

    @Id
    @Column(name = "id_prod", nullable = false)
    private Integer id;

    @Column(name = "COF_NAME", length = 32)
    private String cofName;


    @ToString.Exclude
    @ManyToOne  //(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SUPP_ID")
    private Supplier supplier1;

    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "SALES", nullable = false)
    private Integer sales;

    @Column(name = "TOTAL", nullable = false)
    private Integer total;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_prod")
    private EncriptedCode encriptedCode;

    public Coffee(String cofName, Supplier supplier1, BigDecimal price, Integer sales, Integer total, EncriptedCode ec) {
        this.cofName = cofName;
        this.supplier1 = supplier1;
        this.price = price;
        this.sales = sales;
        this.total = total;
        this.encriptedCode = ec;
    }

    public Coffee(Integer id, String cofName, BigDecimal price, Integer sales, Integer total) {
        this.id = id;
        this.cofName = cofName;
        this.price = price;
        this.sales = sales;
        this.total = total;
    }
}