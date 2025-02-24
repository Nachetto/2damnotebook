package ui;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;

public class ExerciseC {
    ///c. Get the medRecords of a given patient, showing the name of the patient and the total payment
    public static void main(String[] args) {
        MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
        MongoDatabase db = mongo.getDatabase("IgnacioLlorente_hospital");
        MongoCollection<Document> col = db.getCollection("MedRecord");

        MongoCollection<Document> patients = db.getCollection("Patient");

        patients.find().into(new ArrayList<>()).forEach(System.out::println);


        col.aggregate(
                    Arrays.asList(match(eq("patient", "1"))

                )).into(new ArrayList<>()).forEach(System.out::println);

        col = db.getCollection("Patient");
        col.aggregate(Arrays.asList(
                match(eq("name", "Juan Pérez")),
                unwind("$payments"),
                group("$_id", sum("sumValue", "$payments.amount"))
        )).into(new ArrayList<>()).forEach(System.out::println);

        mongo.close();
    }
}
