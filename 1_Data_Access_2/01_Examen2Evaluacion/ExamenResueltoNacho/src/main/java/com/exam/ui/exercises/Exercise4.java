package com.exam.ui.exercises;

import com.exam.dao.utils.MongoUtil;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class Exercise4 {
    public static void main(String[] args) throws ParseException {

        try (MongoUtil mongoUtil = new MongoUtil()) {

            var animalVisits = mongoUtil.getDatabase().getCollection("AnimalVisits");

            // a) total number of tickets (visits) comprados el 15 de Feb al "Elephant"
            animalVisits.aggregate(Arrays.asList(
                    match(eq("date", "2025-02-15")),
                    unwind("$animals"),
                    match(eq("animals.description", "Elephant")),
                    count("totalTickets")
            )).into(new ArrayList<>()).forEach(System.out::println);

            // b) Nombre del animal más visitado
            animalVisits.aggregate(Arrays.asList(
                    unwind("$animals"),
                    group("$animals.name", sum("visits", 1)),
                    sort(descending("visits")),
                    limit(1),
                    project(fields(excludeId(), computed("mostVisitedAnimal", "$_id")))
            )).into(new ArrayList<>()).forEach(System.out::println);

        }
    }
}
