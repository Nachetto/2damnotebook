package CoffeeExampleSpringTemplate.model;

import java.util.Objects;

public class Coffee extends Product {

    private String cof_name;
    private int supp_id;
    private float price;
    private int sales;
    private int total;

    public Coffee() {
    }

    public Coffee(String cof_name, int supp_id, float price, int sales, int total) {
        this.cof_name = cof_name;
        this.supp_id = supp_id;
        this.price = price;
        this.sales = sales;
        this.total = total;
    }

    public String getCof_name() {
        return cof_name;
    }

    public void setCof_name(String cof_name) {
        this.cof_name = cof_name;
    }

    public int getSupp_id() {
        return supp_id;
    }

    public void setSupp_id(int supp_id) {
        this.supp_id = supp_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Coffee{" + "cof_name=" + cof_name + ", supp_id=" + supp_id + ", price=" + price + ", sales=" + sales + ", total=" + total + '}' + super.toString();
    }


      @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coffee that = (Coffee) o;
        return getCof_name() == that.getCof_name();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCof_name());
    }
    
}