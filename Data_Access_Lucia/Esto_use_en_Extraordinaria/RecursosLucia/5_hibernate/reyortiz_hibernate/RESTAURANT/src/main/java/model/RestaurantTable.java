package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = UtilitiesModel.TABLE_RESTAURANT, schema = UtilitiesModel.REYMORTIZ_RESTAURANT)
public class RestaurantTable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = UtilitiesModel.TABLE_NUMBER, nullable = false)
    private int tableNumber;

    @Column(name = UtilitiesModel.NUMBER_SEATS, nullable = false)
    private int seats;
}
