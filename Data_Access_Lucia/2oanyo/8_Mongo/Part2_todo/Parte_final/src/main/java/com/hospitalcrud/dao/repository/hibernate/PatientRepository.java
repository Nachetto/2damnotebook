package com.hospitalcrud.dao.repository.hibernate;


import com.google.gson.Gson;
import com.hospitalcrud.dao.connection.MongoDbConnection;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.hospitalcrud.dao.util.GsonCreator;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
@Profile("mongodb")
@Log4j2
@Data
public class PatientRepository implements PatientDAO {
    private static final String COLLECTION_NAME = "Patient";

    @Override
    public List<Patient> getAll() {
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            List<Patient> patients = new ArrayList<>();
            for (Document doc : collection.find()) {
                Gson gson = GsonCreator.createGson();
                Patient patient = gson.fromJson(doc.toJson(), Patient.class);
                patient.setId(doc.getObjectId("_id"));
                patients.add(patient);
            }
            return patients;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            MongoDbConnection.close();
        }
    }

    @Override
    public int save(Patient patient) {
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            Gson gson = GsonCreator.createGson();
            Document document = Document.parse(gson.toJson(patient));

            document.remove("_id");
            collection.insertOne(document);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            MongoDbConnection.close();
        }
    }

    @Override
    public void update(Patient patient) {
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            Document updateFields = new Document()
                    .append("name", patient.getName())
                    .append("birthDate", patient.getBirthDate().toString())
                    .append("phone", patient.getPhone());

            collection.updateOne(
                    eq("_id", patient.getId()),
                    new Document("$set", updateFields)
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MongoDbConnection.close();
        }
    }

    @Override
    public int delete(ObjectId id, boolean confirmation) {
        int deletedCount = 0;
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            deletedCount = (int) collection.deleteOne(eq("_id", id)).getDeletedCount();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MongoDbConnection.close();
        }
        return deletedCount;
    }

    public Patient getById(ObjectId id) {
        Patient patient = null;
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            Gson gson = GsonCreator.createGson();
            Document doc = collection.find(eq("_id", id)).first();
            if (doc != null) {
                patient = gson.fromJson(doc.toJson(), Patient.class);
                patient.setId(doc.getObjectId("_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            MongoDbConnection.close();
        }
        return patient;
    }
}