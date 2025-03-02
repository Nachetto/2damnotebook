package com.exam.dao.Repository.repositoriesMongo;

import com.google.gson.Gson;
import com.exam.dao.model.mongoModel.AnimalVisitsMongo;
import com.exam.dao.utils.mongo.GsonProvider;
import com.exam.dao.utils.mongo.MongoUtil;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.List;
import java.util.NoSuchElementException;

import static com.mongodb.client.model.Filters.eq;

public class AnimalVisitsRepository {
    private final Gson gson;

    @Inject
    public AnimalVisitsRepository() {
        gson = GsonProvider.createGson();
    }

    public void save(List<AnimalVisitsMongo> animalVisitsMongos) {
        try (MongoUtil mongoUtil = new MongoUtil()) {
            var collection = mongoUtil.getDatabase().getCollection("AnimalVisits");
            animalVisitsMongos.forEach(v->
                    collection.insertOne(Document.parse(gson.toJson(v)))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getVisitFromAnimalName(String name) {
        try (MongoUtil mongoUtil = new MongoUtil()) {
            var collection = mongoUtil.getDatabase().getCollection("AnimalVisits");
            Document document = collection.find(eq("Name",name)).first();
            if (document != null) {
                return gson.fromJson(document.toJson(), AnimalVisitsMongo.class);
            } else {
                throw new NoSuchElementException("No patient found in the collection.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
