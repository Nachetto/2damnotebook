package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Phone {
	private String country;
	private String phoneNr;

    public Phone() {
    }

    public Phone(String country, String phoneNr) {
        this.country = country;
        this.phoneNr = phoneNr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "country='" + country + '\'' +
                ", phoneNr='" + phoneNr + '\'' +
                '}';
    }
}


