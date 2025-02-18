package com.hospitalcrud.dao.repository.hibernate;

import com.google.gson.Gson;
import com.hospitalcrud.dao.connection.MongoDbConnection;
import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.PatientDAO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("mongodb")
public class PatientRepository implements PatientDAO {

    private final MongoCollection<Document> collection;
    private final Gson gson;

    public PatientRepository() {
        this.collection = MongoDbConnection.getDatabase()
                .getCollection("patients");
        this.gson = new Gson();
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        for (Document doc : collection.find()) {
            Patient patient = gson.fromJson(doc.toJson(), Patient.class);
            patient.setId(doc.getObjectId("_id"));
            patients.add(patient);
        }
        return patients;
    }

    @Override
    public int save(Patient patient) {
        Document document = Document.parse(gson.toJson(patient));
        document.remove("_id"); // MongoDB generará el ObjectId
        collection.insertOne(document);
        return 1; // Puedes ajustar esto si necesitas retornar un ID específico
    }

    @Override
    public void update(Patient patient) {
        Bson filter = Filters.eq("_id", patient.getId());
        Document updateFields = Document.parse(gson.toJson(patient));
        updateFields.remove("_id"); // Evita actualizar el ID
        collection.replaceOne(filter, updateFields);
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        if (!confirmation) return false;

        // Aquí necesitarías convertir el int a ObjectId si es necesario
        // Por ejemplo, usando un IdMapper o similar
        Bson filter = Filters.eq("_id", new ObjectId(String.valueOf(id)));
        return collection.deleteOne(filter).getDeletedCount() > 0;
    }


    public Patient getById(int id) {
        Bson filter = Filters.eq("_id", new ObjectId(String.valueOf(id)));
        Document doc = collection.find(filter).first();
        if (doc != null) {
            Patient patient = gson.fromJson(doc.toJson(), Patient.class);
            patient.setId(doc.getObjectId("_id"));
            return patient;
        }
        return null;
    }
}