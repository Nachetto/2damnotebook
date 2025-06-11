package ui;

import dao.model.Visitor;
import domain.error.InternalServerErrorException;
import domain.service.VisitorService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Exercise2 {
    private static final Logger log = LoggerFactory.getLogger(Exercise2.class);

    public static void main(String[] args) {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        VisitorService service = container.select(VisitorService.class).get();
        log.info("\n \n \n \n \n \n \n");


        Visitor v = new Visitor(999,"David Jhonson", "david.jhonson@example.com", 3);
        try{
            if (service.add(v)>0)
                log.info("The visitor was succesfully created.");
        }catch (InternalServerErrorException e){
            log.error("There was an error while trying to add the new visitor \n"+v+"\nReason: \n"+e.getMessage());
        }

        log.info("\n \n \n \n \n \n \n");
        weld.shutdown();
    }
}
