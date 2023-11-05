package JDBCCoffeeExampleWithPool;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.HashMap;


public class Main {

	public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        CoffeeDAO coffeeDAO = container.select(CoffeeDAO.class).get();

        System.out.println("List of coffees:");

        coffeeDAO.getAll();

        coffeeDAO.updateCoffeePrices(5);

        coffeeDAO.saveAndDeleteWithRS(17,"coffee15", 101, 200, 10, 470);


        HashMap<String, Integer> sales = new HashMap<String, Integer>();
        sales.put("Colombian", 175);
        sales.put("French_Roast", 150);
        sales.put("Espresso", 60);
        sales.put("Colombian_Decaf", 155);
        sales.put("French_Roast_Decaf", 90);

        coffeeDAO.updateSales(sales);

        coffeeDAO.getAll();

        }
}
