/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import lombok.*;
import org.bson.types.ObjectId;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Builder @ToString

public class Supplier {

    private ObjectId supp_id;
    private String street;
    private String town;
    private String country;
    private String pcode;

    @Singular
    private List<Coffee> coffees;

}