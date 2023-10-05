package dao.model;

public class Customer {
    public Customer(String username, String password){
        Credential credential = new Credential(username, password);
    }
}
