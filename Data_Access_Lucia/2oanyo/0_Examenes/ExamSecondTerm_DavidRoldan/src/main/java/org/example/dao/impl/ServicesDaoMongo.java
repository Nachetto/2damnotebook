package org.example.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.dao.ServicesDAO;
import org.example.dao.adapters.LocalDateAdapter;
import org.example.dao.adapters.LocalDateTimeAdapter;
import org.example.dao.adapters.ObjectIdAdapter;
import org.example.model.ServicesEntity;
import org.example.model.errors.GymError;
import org.example.model.mongo.GymServices;
import org.example.model.mongo.GymSubscriptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ServicesDaoMongo {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ObjectId.class, new ObjectIdAdapter())
            .create();


    public Either<GymError, GymServices> get(String name) {
        Either<GymError, GymServices> result = null;
        try (MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.wompwomp.com:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_gym");
            MongoCollection<Document> services = db.getCollection("services");

            Document document = services.find(eq("name", name)).first();

            if (document!=null){
                GymServices gymServices = convertDocumentToGymServices(document);
                result = Either.right(gymServices);
            } else {
                result = Either.left(new GymError("Error obtaining service"));
            }
        } catch (Exception e) {
            result = Either.left(new GymError("Error obtaining service"));
        }
        return result;
    }

    private GymServices convertDocumentToGymServices(Document document){
        return gson.fromJson(document.toJson(), GymServices.class);
    }
}
