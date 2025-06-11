import dao.CoffeeHibDAO;
import dao.SupplierHibDAO;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.persistence.PersistenceException;
import model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(final String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        CoffeeHibDAO cdao = container.select(CoffeeHibDAO.class).get();
        SupplierHibDAO sdao = container.select(SupplierHibDAO.class).get();

//List coffees
        List<Coffee> listCoffees;
        listCoffees = cdao.getAll();
        System.out.println("List of coffees: ");
        listCoffees.forEach((c) -> {
            System.out.println(c);
        });

//---------One to one relationship

        //Delete: Cascade.REMOVE
//           Coffee c= cdao.get(36);
//           System.out.println("Deleting Coffee: "+c);
//          cdao.delete(c);


        //Add coffee
//        Supplier s= sdao.get(49);
//        EncriptedCode ec = new EncriptedCode(1000);
//        Coffee newCoffee= new Coffee("coffNew", s, new BigDecimal(30),50, 4000, ec);
//        cdao.add(newCoffee);


// ---------Many to one relationship

        System.out.println(cdao.get(new Coffee(null,"coffNew",null,null,null)));

        //ADD
//        Supplier s= new Supplier(52, "bla", "bla", "bl");
//        EncriptedCode ec = new EncriptedCode(1000);
//        Coffee newCoffee= new Coffee("coffNew", s, new BigDecimal(30),50, 4000, ec);
//
//        //When adding a coffee, ¿the supplier must exist?
//
//        //Option1: Add without adding supplier: Handle Exception
//            cdao.add(newCoffee);

        //Option2:  If cascade Persist, the new supplier is added
        //cdao.add(newCoffee);



        //Delete: When deleting a coffee, the supplier does not change
        //cdao.delete(cdao.get(3));


// ---------One to many relationship

// List suppliers (Exclude Supplier from coffee @ToString method)
//        List<Supplier> listSuppliers;
//        listSuppliers = sdao.getAll();
//        System.out.println("List of suppliers: ");
//        listSuppliers.forEach((c) -> {
//            System.out.println(c);
//        });

        //ADD: Add a supplier
//        Supplier s= new Supplier(53, "bla", "bla", "bl");
//        sdao.add(s);

        //REMOVE: Delete a supplier. If coffees, ask the user --Avoid Cascade.REMOVE
//        sdao.delete(sdao.get(52));
    }
}