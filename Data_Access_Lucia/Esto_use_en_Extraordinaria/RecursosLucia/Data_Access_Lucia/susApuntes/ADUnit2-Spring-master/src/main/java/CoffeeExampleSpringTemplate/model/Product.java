package CoffeeExampleSpringTemplate.model;

public class Product {

    private int id_prod;
    private String country;
    private EuropeanCode eu;
    

    public Product() {
    }
    
    public Product(int id_prod, String country, EuropeanCode eu) {
        this.id_prod = id_prod;
        this.country = country;
        this.eu = eu;
    }
    
    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public EuropeanCode getEu() {
        return eu;
    }

    public void setEu(EuropeanCode eu) {
        this.eu = eu;
    }

    @Override
    public String toString() {
        return "Product{" + "id_prod=" + id_prod + ", country=" + country + ", eu=" + eu + '}';
    }

    

    
    
}