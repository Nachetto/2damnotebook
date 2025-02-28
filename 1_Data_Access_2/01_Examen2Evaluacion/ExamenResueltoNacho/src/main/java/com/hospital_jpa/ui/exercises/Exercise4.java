package com.hospital_jpa.ui.exercises;

import com.hospital_jpa.dao.utils.MongoUtil;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;

public class Exercise4 {
    public static void main(String[] args) throws ParseException {

        try (MongoUtil mongoUtil = new MongoUtil()) {

            var animalVisits = mongoUtil.getDatabase().getCollection("AnimalVIsits");

            animalVisits.aggregate(Arrays.asList(
                    match(eq("date", new SimpleDateFormat("yyyy-MM-dd").parse("2023-02-15"))),
                    unwind("$animals"),
                    match(eq("animals.name", "elephant")),
                    group(null, sum("totalTickets", 1))
            )).into(new ArrayList<>()).forEach(System.out::println);


            animalVisits.aggregate(Arrays.asList(
                    unwind("$animals"),
                    group("$animals.name", sum("totalVisits", 1)),
                    sort(new Document("totalVisits", -1)),
                    limit(1),
                    project(new Document("_id", 0).append("mostVisitedAnimal", "$_id"))
            )).into(new ArrayList<>()).forEach(System.out::println);
        }
    }
}
