package ui;

import domain.error.InternalServerErrorException;
import domain.service.VisitService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Exercise1 {
    private static final Logger log = LoggerFactory.getLogger(Exercise1.class);

    public static void main(String[] args) {

        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        VisitService service = container.select(VisitService.class).get();
        log.info("\n \n \n \n \n \n \n");

        try{
            if (service.loadFromXmlToDatabase()==1)
                log.info("Data loaded to the database succesfully");
        }catch (InternalServerErrorException e){
            log.error("There was an error during exercise 1, see: \n"+e.getMessage());
        }

        log.info("\n \n \n \n \n \n \n");
        weld.shutdown();
    }

}
