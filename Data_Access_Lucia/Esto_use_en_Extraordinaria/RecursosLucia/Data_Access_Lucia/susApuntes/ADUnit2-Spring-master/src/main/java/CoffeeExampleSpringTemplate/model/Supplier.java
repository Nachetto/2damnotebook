package CoffeeExampleSpringTemplate.model;

public class Supplier {

    private int supp_id;
    private String street;
    private String town;
    private String country;
    private String pcode;

    public Supplier() {
    }

    public Supplier(int supp_id, String street, String town, String country) {
        this.supp_id = supp_id;
        this.street = street;
        this.town = town;
        this.country = country;
    }

    public int getSupp_id() {
        return supp_id;
    }

    public void setSupp_id(int supp_id) {
        this.supp_id = supp_id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    @Override
    public String toString() {
        return "Supplier{" + "supp_id=" + supp_id + ", street=" + street + ", town=" + town + ", country=" + country + ", pcode=" + pcode + '}';
    }

    
}