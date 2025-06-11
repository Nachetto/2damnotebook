/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "suppliers")

@NamedQueries({ @NamedQuery(name = "HQL_GET_ALL_SUPPLIERS",
        query = "from BasicSupplier") })

public class BasicSupplier {

    @Id

    @Column(name = "SUPP_ID")
    private int supp_id;

    //Name ignored

    @Column(name = "STREET")
    private String street;

    //Town not persistent
    private String town;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PCODE")
    private String pcode;

}