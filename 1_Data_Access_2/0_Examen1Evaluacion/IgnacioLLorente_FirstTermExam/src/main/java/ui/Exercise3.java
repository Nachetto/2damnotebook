package ui;

import dao.model.Animal;
import domain.error.InternalServerErrorException;
import domain.service.AnimalService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class Exercise3 {
    private static final Logger log = LoggerFactory.getLogger(Exercise3.class);

    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        AnimalService service = container.select(AnimalService.class).get();
        log.info("\n \n \n \n \n \n \n");

        try {
            Animal animal = service.getFromUsername("Nemo");
            if (service.hasAssociatedVisits(animal)) {
                log.info("This animal has associated visits, confirm deletion? yes/no");
                Scanner sc = new Scanner(System.in);
                String result = sc.next();
                sc.nextLine();
                if (result.equalsIgnoreCase("yes"))
                    service.delete(animal, true);
            } else {
                service.delete(animal, false);
            }
        } catch (InternalServerErrorException e) {
            log.error("there was an error while trying to delete the Animal. See: \n" + e.getMessage());
        }


        log.info("\n \n \n \n \n \n \n");
        weld.shutdown();
    }
}
