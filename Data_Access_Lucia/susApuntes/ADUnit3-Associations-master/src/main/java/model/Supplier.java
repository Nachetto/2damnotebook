/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "suppliers")

//Annotations in getters when not using Lombok

public class Supplier {

    private int supp_id;
    private String street;
    //Town not persistent
    private String town;
    private String country;
    private String pcode;
    private List<Coffee> coffees;


    public Supplier() {
    }

    public Supplier(int supp_id, String street, String town, String country) {
        this.supp_id = supp_id;
        this.street = street;
        this.town = town;
        this.country = country;

    }

    public Supplier(int id) {
        this.supp_id = id;
    }

    public Supplier(int supp_id, String street, String town, String country, String pcode, List<Coffee> coffees) {
        this.supp_id = supp_id;
        this.street = street;
        this.town = town;
        this.country = country;
        this.pcode = pcode;
        this.coffees = coffees;
    }

    @Id
    @Column(name = "SUPP_ID")
    public int getSupp_id() {
        return supp_id;
    }

    public void setSupp_id(int supp_id) {
        this.supp_id = supp_id;
    }

    @Column(name = "STREET")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "PCODE")
    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

//    @OneToMany(mappedBy = "supplier1" /*, fetch = FetchType.EAGER*/)
//    public List<Coffee> getCoffees() {
//        return coffees;
//    }

    public void setCoffees(List<Coffee> coffees) {
        this.coffees = coffees;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supp_id=" + supp_id +
                ", street='" + street + '\'' +
                ", town='" + town + '\'' +
                ", country='" + country + '\'' +
                ", pcode='" + pcode + '\'' +
                ", coffees=" + coffees +
                '}';
    }
}