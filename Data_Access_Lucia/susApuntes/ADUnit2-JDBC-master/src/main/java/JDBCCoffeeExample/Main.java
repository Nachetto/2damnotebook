package JDBCCoffeeExample;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.HashMap;



public class Main {

	public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        BasicCoffeeDAO coffeeDAO = container.select(BasicCoffeeDAO.class).get();

            System.out.println("List of coffees:");

            coffeeDAO.getAll();

            System.out.println("List of Colombian coffees:");

            coffeeDAO.get("Colombian");

            coffeeDAO.updateCoffeeSales("Colombian", 678);

            System.out.println(coffeeDAO.save("Sudafrica250", 101, 200, 10, 470) + " rows saved");

            coffeeDAO.getAll();

            System.out.println(coffeeDAO.delete(12) + " rows deleted");

//            coffeeDAO.updateCoffeePrices(5);
//
//                HashMap<String, Integer> sales = new HashMap<String, Integer>();
//                sales.put("Colombian", 175);
//                sales.put("French_Roast", 150);
//                sales.put("Espresso", 60);
//                sales.put("Colombian_Decaf", 155);
//                sales.put("French_Roast_Decaf", 90);
//
//            coffeeDAO.updateSales(sales);
//
//            coffeeDAO.getAll();

        }
}
