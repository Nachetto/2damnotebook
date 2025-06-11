import model.BasicSupplier;
import dao.BasicSupplierHibDAO;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.List;

public class Main {

       public static void main(final String[] args)  {
           SeContainerInitializer initializer = SeContainerInitializer.newInstance();
           final SeContainer container = initializer.initialize();

           BasicSupplierHibDAO bsdao = container.select(BasicSupplierHibDAO.class).get();

           List<BasicSupplier> listSuppliers;
           listSuppliers = bsdao.getAll();
           System.out.println("List of Suppliers: ");
           listSuppliers.forEach((c) -> {
               System.out.println(c);
           });

           System.out.println("Supplier 49: " + bsdao.get(49));

           System.out.println("Adding new supplier");
           BasicSupplier newSupp= new BasicSupplier(1,"st. bla bla", "Madrid", "SP", "23454");
           bsdao.add(newSupp);
           System.out.println("Supplier 1 added: " + bsdao.get(1));

           System.out.println("Modifying supplier's address");
           newSupp.setStreet("Paseo del prado");
           bsdao.update(newSupp);

           System.out.println("Supplier 1 modified: " + bsdao.get(1));

           System.out.println("Deleting supplier 1");
           bsdao.delete(newSupp);

           listSuppliers = bsdao.getAll();
           System.out.println("List of Suppliers: ");
           listSuppliers.forEach((c) -> {
               System.out.println(c);
           });

       }
}