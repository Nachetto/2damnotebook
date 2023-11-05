package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
	private String street;
	private int number;

    public Address() {
    }

    public Address(String street, int number) {
        this.street = street;
        this.number = number;
    }
	
    public String getStreet() {
            return street;
    }
    public void setStreet(String street) {
            this.street = street;
    }
    public int getNumber() {
            return number;
    }
    public void setNumber(int number) {
            this.number = number;
    }
    @Override
    public String toString() {
            return "Address [Street=" + street + ", number=" + number + "]";
    }
	
}


