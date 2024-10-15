package org.example.dao.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import io.vavr.control.Either;
import org.bson.Document;
import org.example.common.Constants;
import org.example.dao.AggregationDAO;
import org.example.model.errors.GymError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Indexes.descending;
import static com.mongodb.client.model.Projections.*;

public class AggregationDaoImpl implements AggregationDAO {
    @Override
    public Either<GymError, String> exA() {
        Either<GymError, String> result;
        try (MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_gym");
            MongoCollection<Document> subscriptions = db.getCollection("subscriptions");

            List<Document> documents = subscriptions.aggregate(
                    Arrays.asList(
                            unwind("$services"),
                            lookup("services", "services", "_id", "service"),
                            unwind("$service"),
                            match(eq("service.name", "Yoga Classes")),
                            group("$client.name", sum("totalPrice", "$service.price")
                            )
                    )).into(new ArrayList<>());

            if (!documents.isEmpty()) {
                List<String> jsonDocuments = new ArrayList<>();
                for (org.bson.Document document : documents) {
                    jsonDocuments.add(document.toJson());
                }
                String jsonResult = "[" + String.join(",", jsonDocuments) + "]";
                result = Either.right(jsonResult);
            } else {
                result = Either.left(new GymError(Constants.NO_RESULTS));
            }
        } catch (Exception e) {
            result = Either.left(new GymError("Error updating subscription"));
        }
        return result;
    }

    @Override
    public Either<GymError, String> exB() {
        Either<GymError, String> result;
        try (MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_gym");
            MongoCollection<Document> subscriptions = db.getCollection("subscriptions");

            List<Document> documents = subscriptions.aggregate(
                    Arrays.asList(
                            unwind("$services"),
                            lookup("services", "services", "_id", "service"),
                            unwind("$service"),
                            group("$client.name", sum("totalPrice", "$service.price")),
                            sort(descending("totalPrice")),
                            limit(1),
                            project(fields(computed("Name", "$_id"), excludeId()))
                    )
            ).into(new ArrayList<>());

            if (!documents.isEmpty()) {
                List<String> jsonDocuments = new ArrayList<>();
                for (org.bson.Document document : documents) {
                    jsonDocuments.add(document.toJson());
                }
                String jsonResult = "[" + String.join(",", jsonDocuments) + "]";
                result = Either.right(jsonResult);
            } else {
                result = Either.left(new GymError(Constants.NO_RESULTS));
            }
        } catch (Exception e) {
            result = Either.left(new GymError("Error updating subscription"));
        }
        return result;
    }

    @Override
    public Either<GymError, String> exC() {
        Either<GymError, String> result;
        try (MongoClient mongo = MongoClients.create("mongodb://dam2.tomcat.iesquevedo.es:2323")) {
            MongoDatabase db = mongo.getDatabase("luciasanmiguel_gym");
            MongoCollection<Document> subscriptions = db.getCollection("subscriptions");

            List<Document> documents = subscriptions.aggregate(
                    Arrays.asList(
                            unwind("$services"),
                            group("$services", sum("timesRequested", 1)),
                            sort(descending("timesRequested")),
                            limit(1),
                            lookup("services", "_id", "_id", "service"),
                            unwind("$service"),
                            project(fields(computed("Service", "$service.name"), excludeId()))

                    )
            ).into(new ArrayList<>());

            if (!documents.isEmpty()) {
                List<String> jsonDocuments = new ArrayList<>();
                for (org.bson.Document document : documents) {
                    jsonDocuments.add(document.toJson());
                }
                String jsonResult = "[" + String.join(",", jsonDocuments) + "]";
                result = Either.right(jsonResult);
            } else {
                result = Either.left(new GymError(Constants.NO_RESULTS));
            }
        } catch (Exception e) {
            result = Either.left(new GymError("Error updating subscription"));
        }
        return result;
    }
}


