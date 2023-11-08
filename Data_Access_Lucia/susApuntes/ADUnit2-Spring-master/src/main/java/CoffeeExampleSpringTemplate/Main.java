package CoffeeExampleSpringTemplate;


import CoffeeExampleSpringTemplate.dao.ProductDAO;
import CoffeeExampleSpringTemplate.dao.SupplierDAO;
import CoffeeExampleSpringTemplate.model.Supplier;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Main {

	public static void main(String[] args) throws Exception {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        SupplierDAO mySupplier = container.select(SupplierDAO.class).get();

          System.out.println("List of suppliers:");

          mySupplier.getAllSuppliers().forEach(System.out::println);

        ProductDAO myProduct = container.select(ProductDAO.class).get();
//        System.out.println("List of products:");
//
//        myProduct.getAllProducts().forEach(System.out::println);
//        myProduct.getAllProductsWithEUCode().forEach(System.out::println);
        myProduct.getAllProductsWithEUCodeUsingRowMapperSingleClass().forEach(System.out::println);

//        CoffeeDAO myCoffee = container.select(CoffeeDAO.class).get();
//        System.out.println("List of coffees:");
//
//        myCoffee.getAllCoffees().forEach(System.out::println);
//        myCoffee.getAllCoffeesWithEUCode().forEach(System.out::println);
//
//            
            Supplier s= mySupplier.getSupplier(49);
            System.out.println(s);

            System.out.println("Number of suppliers: " + mySupplier.getNumberOfSuppliers());


//            
//            s.setStreet("145-67 St");
//            s.setCountry("US");
//
//            int res= mySupplier.updateSupplier(s);
//            if (res>0)
//                System.out.println("Successful update");
//            else
//                System.out.println("Error when updating");
//            
//            int res= mySupplier.deleteSupplier(150);
//            if (res>0)
//                System.out.println("Successful delete");
//            else if (res == -2)
//                System.out.println("Integrity violation");
//                else
//                System.out.println("Error when deleting");
//         
//            int res = mySupplier.deleteSupplierWithCoffees(150);
//            if (res>0)
//                System.out.println("Successful delete");
//            else
//                System.out.println("Error when deleting");
//            
//            Supplier s2 = new Supplier();
//            s2.setSupp_id(150);
//            s2.setStreet("1-67 St");
//            s2.setCountry("UK");
//            int res= mySupplier.addSupplier(s2);
//            if (res>0)
//                System.out.println("Successful insertion");
//            else
//                System.out.println("Error when inserting");

        }
}
