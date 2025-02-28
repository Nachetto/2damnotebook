package com.hospital_jpa.dao.repositoriesMongo;

import com.google.gson.Gson;
import com.hospital_jpa.dao.model.mongoModel.AnimalVisitsMongo;
import com.hospital_jpa.dao.utils.GsonProvider;
import com.hospital_jpa.dao.utils.MongoUtil;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.List;

public class AnimalVisitsRepository {
    private final Gson gson;

    @Inject
    public AnimalVisitsRepository() {
        gson = GsonProvider.createGson();
    }

    public void save(List<AnimalVisitsMongo> animalVisitsMongos) {
        try (MongoUtil mongoUtil = new MongoUtil()) {
            var collection = mongoUtil.getDatabase().getCollection("AnimalVisits");

            animalVisitsMongos.forEach(v->collection.insertOne(Document.parse(gson.toJson(v))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
