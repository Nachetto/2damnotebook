package ui;

import com.mongodb.MongoException;
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

public class ExerciseD {
    public static void main(String[] args) {
        MongoClient mongo = null;
        try {
            mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
            MongoDatabase db = mongo.getDatabase("IgnacioLlorente_hospital");
            MongoCollection<Document> col = db.getCollection("MedRecord");

            // Debug: Verificar si la colección MedRecord tiene datos
            System.out.println("MedRecord collection documents:");
            col.find().into(new ArrayList<>()).forEach(System.out::println);

            // Agregación para obtener los registros médicos de un paciente
            System.out.println("MedRecords for patient 1:");
            col.aggregate(
                    Arrays.asList(match(eq("patient", "1")))
            ).into(new ArrayList<>()).forEach(System.out::println);

            col = db.getCollection("Patient");

            // Debug: Verificar si la colección Patient tiene datos
            System.out.println("Patient collection documents:");
            col.find().into(new ArrayList<>()).forEach(System.out::println);

            // Agregación para obtener el nombre del paciente y el pago total
            System.out.println("Total payments for Juan Pérez:");
            col.aggregate(Arrays.asList(
                    match(eq("name", "Juan Pérez")),
                    unwind("$payments"),
                    group("$_id", sum("sumValue", "$payments.amount"))
            )).into(new ArrayList<>()).forEach(System.out::println);

        } catch (MongoException e) {
            System.err.println("An error occurred while connecting to MongoDB: " + e.getMessage());
        } finally {
            if (mongo != null) {
                mongo.close();
            }
        }
    }
}