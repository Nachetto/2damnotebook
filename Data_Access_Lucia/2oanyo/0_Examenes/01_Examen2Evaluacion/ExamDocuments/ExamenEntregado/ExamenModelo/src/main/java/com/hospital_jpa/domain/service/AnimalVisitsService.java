package com.hospital_jpa.domain.service;

import com.google.gson.Gson;
import com.hospital_jpa.dao.model.hibernateModel.AnimalHibernate;
import com.hospital_jpa.dao.model.hibernateModel.AnimalVisitsHibernate;
import com.hospital_jpa.dao.model.hibernateModel.VisitorHibernate;
import com.hospital_jpa.dao.model.mongoModel.AnimalMongo;
import com.hospital_jpa.dao.model.mongoModel.AnimalVisitsMongo;
import com.hospital_jpa.dao.model.mongoModel.VisitorMongo;
import com.hospital_jpa.dao.repositoriesHibernate.AnimalVisitsRepository;
import com.hospital_jpa.dao.utils.GsonProvider;
import com.hospital_jpa.dao.utils.MongoUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnimalVisitsService {
    private final Gson gson;
    private final VisitorService visitorService;
    private final HabitatService habitatService;
    private final AnimalService animalService;
    private final AnimalVisitsRepository hibRepo;
    @Inject
    public AnimalVisitsService(VisitorService visitorService,HabitatService habitatService,AnimalVisitsRepository repository,AnimalService animalService){
        this.visitorService=visitorService;
        this.habitatService=habitatService;
        this.hibRepo =repository;
        this.animalService=animalService;
        gson = GsonProvider.createGson();
    }
    public void registerVisit(String visitorName, String habitatName){
        //get the visitor id from the name
        int visitorID = visitorService.get(visitorName);
        //get the habitat id from the name
        int habitatID = habitatService.get(habitatName);
        //get the animal id from the habitat id
        AnimalHibernate animalID = animalService.get(habitatID);
        //add the visit with the animal id, visitor id and todays date
        hibRepo.add(visitorID,animalID, Date.from(Instant.now()));
    }
    public List<AnimalVisitsHibernate> getAll(){
        return hibRepo.getAll();
    }

    public void saveVisits(){
        List<AnimalVisitsHibernate> hibernateList = getAll();
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

        try (MongoUtil mongoUtil = new MongoUtil()) {

            var collection = mongoUtil.getDatabase().getCollection("AnimalVIsits");

            Document document = Document.parse(gson.toJson(visitsMongo));

            if (document != null) {
                collection.insertOne(document);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
