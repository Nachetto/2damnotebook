package com.exam.domain.service;

import com.exam.dao.model.hibernateModel.AnimalHibernate;
import com.exam.dao.model.hibernateModel.AnimalVisitsHibernate;
import com.exam.dao.model.hibernateModel.VisitorHibernate;
import com.exam.dao.model.mongoModel.AnimalMongo;
import com.exam.dao.model.mongoModel.AnimalVisitsMongo;
import com.exam.dao.model.mongoModel.VisitorMongo;
import com.exam.dao.repositoriesHibernate.AnimalVisitsRepository;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnimalVisitsService {
    private final VisitorService visitorService;
    private final HabitatService habitatService;
    private final AnimalService animalService;
    private final AnimalVisitsRepository hibRepo;
    private final com.exam.dao.repositoriesMongo.AnimalVisitsRepository mongoRepo;

    @Inject
    public AnimalVisitsService(VisitorService visitorService, HabitatService habitatService, AnimalVisitsRepository repository, AnimalService animalService, com.exam.dao.repositoriesMongo.AnimalVisitsRepository mongoRepo){
        this.visitorService=visitorService;
        this.habitatService=habitatService;
        this.hibRepo =repository;
        this.animalService=animalService;
        this.mongoRepo = mongoRepo;
    }

    public void registerVisit(String visitorName, String habitatName){
        //get the visitor id from the name
        int visitorID = visitorService.get(visitorName);
        //get the animal id from the habitat id
        AnimalHibernate animalID = animalService.get(habitatName);
        //add the visit with the animal id, visitor id and todays date
        hibRepo.add(visitorID,animalID, Date.from(Instant.now()));
    }

    public List<AnimalVisitsHibernate> getAll(){
        return hibRepo.getAll();
    }

    private List<AnimalVisitsMongo> hibernateVisitsToMongo(List<AnimalVisitsHibernate> hibernateList){
        List<AnimalVisitsMongo> visitsMongo = new ArrayList<>();
        for (AnimalVisitsHibernate visitHibernate: hibernateList){
            List<AnimalMongo> animalMongos= new ArrayList<>();
            VisitorHibernate visitorHibernate = visitorService.get(visitHibernate.getVisitor_ID());
            animalMongos.add(
                    new AnimalMongo(
                            visitHibernate.getAnimal_ID().getName(),
                            visitHibernate.getAnimal_ID().getSpecies()
                    )
            );
            AnimalVisitsMongo visitMongo = new AnimalVisitsMongo();
            visitMongo.setId(new ObjectId());
            visitMongo.setDate(visitHibernate.getDate());
            visitMongo.setAnimals(animalMongos);
            visitMongo.setVisitor(
                    new VisitorMongo(
                            visitorHibernate.getName(),
                            visitorHibernate.getTickets()
                    )
            );
            visitsMongo.add(visitMongo);
        }
        return visitsMongo;
    }

    //meter una nueva visita para

    public void saveVisits(){
        List<AnimalVisitsHibernate> hibernateList = getAll();
        mongoRepo.save(hibernateVisitsToMongo(hibernateList));
    }
}
