package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = UtilitiesModel.MENU_ITEM, schema = UtilitiesModel.REYMORTIZ_RESTAURANT)
public class MenuItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = UtilitiesModel.IDMENU_ITEM, nullable = false)
    private int id;

    @Column(name = UtilitiesModel.NAME, nullable = false, length = 45)
    private String name;

    @Column(name = UtilitiesModel.DESCRIPTION, nullable = true, length = 45)
    private String description;

    @Column(name = UtilitiesModel.PRICE, nullable = false, precision = 0)
    private double price;
}
