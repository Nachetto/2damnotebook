package org.example.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.dao.SubscriptionsDAO;
import org.example.dao.adapters.LocalDateAdapter;
import org.example.dao.adapters.LocalDateTimeAdapter;
import org.example.dao.adapters.ObjectIdAdapter;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;
import org.example.model.mongo.GymSubscriptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class SubscriptionsDaoMongo {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ObjectId.class, new ObjectIdAdapter())
            .create();

    public Either<GymError, GymSubscriptions> get(String name) {
        Either<GymError, GymSubscriptions> result = null;
        try (MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_gym");
            MongoCollection<Document> subscriptions = db.getCollection("subscriptions");

            // Find the document with the specified ID
            List<Document> documents = subscriptions.find(eq("client.name", name)).into(new ArrayList<>());


            if (documents != null) {
                List<GymSubscriptions> gymSubscriptionsList = new ArrayList<>();
                for (Document document : documents) {
                    gymSubscriptionsList.add(convertDocumentToGymSubscription(document));
                }
                List<GymSubscriptions> goodSubscription = new ArrayList<>();
                for (GymSubscriptions sub : gymSubscriptionsList) {
                    if (sub.getEndDate().isAfter(LocalDate.now()) && sub.getStartDate().isBefore(LocalDate.now())) {
                        goodSubscription.add(sub);
                    }
                }
                if (goodSubscription.isEmpty()) {
                    result = Either.left(new GymError("No active subscriptions"));
                } else {
                    result = Either.right(goodSubscription.get(0));
                }
            } else {
                result = Either.left(new GymError("Error obtaining sub"));
            }
        } catch (Exception e) {
            result = Either.left(new GymError("Error obtaining sub"));
        }
        return result;
    }

    public Either<GymError, Integer> save(GymSubscriptions gymSubscriptions){
        Either<GymError, Integer> result = null;
        try (MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_gym");
            MongoCollection<Document> subscriptions = db.getCollection("GymSubscriptionsBackup");

            Document newSubscription = Document.parse(gson.toJson(gymSubscriptions));

            subscriptions.insertOne(newSubscription);

            result = Either.right(0);
        } catch (Exception e) {
            result = Either.left(new GymError("Error updating subscription"));
        }
        return result;

    }

    public Either<GymError, Integer> update(GymSubscriptions gymSubscriptions){
        Either<GymError, Integer> result = null;
        try (MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_gym");
            MongoCollection<Document> subscriptions = db.getCollection("subscriptions");

            Document updatedSubscription = Document.parse(gson.toJson(gymSubscriptions));

            subscriptions.replaceOne(Filters.eq("_id", gymSubscriptions.get_id()), updatedSubscription);

            result = Either.right(0);
        } catch (Exception e) {
            result = Either.left(new GymError("Error updating subscription"));
        }
        return result;
    }

    private GymSubscriptions convertDocumentToGymSubscription(Document document) {
        return gson.fromJson(document.toJson(), GymSubscriptions.class);
    }
}
