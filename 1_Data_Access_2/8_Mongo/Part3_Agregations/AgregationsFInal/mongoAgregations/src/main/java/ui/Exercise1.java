package ui;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;

public class Exercise1 {
        public static void main(String[] args) {
            MongoClient mongo = MongoClients.create("mongodb://informatica.iesquevedo.es:2323");
            MongoDatabase db = mongo.getDatabase("IgnacioLlorente_hospital");

            // a. Get the oldest patient
            MongoCollection<Document> patients = db.getCollection("Patient");
            patients.aggregate(Arrays.asList(
                    sort(new Document("birthDate", 1)),
                    limit(1)
            )).into(new ArrayList<>()).forEach(System.out::println);

            // b. Get the name of the patient who has paid the most
            patients.aggregate(Arrays.asList(
                    unwind("$payments"),
                    group("$name", sum("totalPaid", "$payments.amount")),
                    sort(new Document("totalPaid", -1)),
                    limit(1)
            )).into(new ArrayList<>()).forEach(System.out::println);

            // c. Get the medRecords of a given patient, showing the name of the patient and the total payment
            System.out.println("MedRecords for patient 1:");
            patients.aggregate(
                    Arrays.asList(
                            match(eq("patient", "1")),
                            lookup("Patient", "patient", "_id", "patientInfo"),
                            unwind("$patientInfo"),
                            project(new Document("patientName", "$patientInfo.name").append("totalPayment", "$patientInfo.payments.amount"))
                    )
            ).into(new ArrayList<>()).forEach(System.out::println);

            // d. Get the number of medications of each medRecord
            MongoCollection<Document> medRecords = db.getCollection("MedRecord");
            medRecords.aggregate(Arrays.asList(
                    project(new Document("_id", 1).append("medCount", new Document("$size", "$medications")))
            )).into(new ArrayList<>()).forEach(System.out::println);

            // e. Get the name of the patients who have been prescribed Ibuprofen
            System.out.println("Patients prescribed Ibuprofen:");
            medRecords.aggregate(Arrays.asList(
                    unwind("$medications"),
                    match(eq("medications.name", "Ibuprofen")),
                    lookup("Patient", "patientId", "_id", "patientInfo"),
                    unwind("$patientInfo"),
                    project(new Document("_id", 0).append("patientName", "$patientInfo.name"))
            )).into(new ArrayList<>()).forEach(System.out::println);

            // f. Get the average number of medications per medRecord
            medRecords.aggregate(Arrays.asList(
                    group(null, avg("avgMedications", new Document("$size", "$medications")))
            )).into(new ArrayList<>()).forEach(System.out::println);

            // g. Get the medication most prescribed
            medRecords.aggregate(Arrays.asList(
                    unwind("$medications"),
                    group("$medications.name", sum("count", 1)),
                    sort(new Document("count", -1)),
                    limit(1)
            )).into(new ArrayList<>()).forEach(System.out::println);

            // h. Get the most prescribed medication per patient
            medRecords.aggregate(Arrays.asList(
                    unwind("$medications"),
                    group(Arrays.asList("$patientId", "$medications.name"), sum("count", 1)),
                    sort(new Document("count", -1)),
                    group("$_id.0", first("mostPrescribed", "$_id.1"))
            )).into(new ArrayList<>()).forEach(System.out::println);

            // i. Get the doctor who prescribes more medications
            medRecords.aggregate(Arrays.asList(
                    unwind("$medications"),
                    group("$doctorId", sum("totalPrescriptions", 1)),
                    sort(new Document("totalPrescriptions", -1)),
                    limit(1),
                    lookup("Doctor", "_id", "_id", "doctorInfo"),
                    unwind("$doctorInfo"),
                    project(new Document("_id", 0).append("doctorName", "$doctorInfo.name"))
            )).into(new ArrayList<>()).forEach(System.out::println);



            mongo.close();
        }
}
