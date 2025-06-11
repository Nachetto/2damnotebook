package com.hospitalcrud.dao.repository.hibernate;


import com.google.gson.Gson;
import com.hospitalcrud.dao.connection.MongoDbConnection;
import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repository.DoctorDAO;
import com.hospitalcrud.dao.util.GsonCreator;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("mongodb")
@Log4j2
public class DoctorRepository implements DoctorDAO {
    private static final String COLLECTION_NAME = "Doctor";

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            for (Document doc : collection.find()) {
                Gson gson = GsonCreator.createGson();
                Doctor doctor = gson.fromJson(doc.toJson(), Doctor.class);
                doctor.setId(doc.getObjectId("_id"));
                doctors.add(doctor);
            }

        } catch (Exception e) {
            log.error("Error getting all doctors", e);
        } finally {
            MongoDbConnection.close();
        }
        return doctors;
    }

    @Override
    public Doctor get(ObjectId idDoctor) {
        Doctor doctor = null;
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("_id", idDoctor);
            Document doc = collection.find(query).first();

            if (doc != null) {
                Gson gson = GsonCreator.createGson();
                doctor = gson.fromJson(doc.toJson(), Doctor.class);
                doctor.setId(doc.getObjectId("_id"));
            }
        } catch (Exception e) {
            log.error("Error getting doctor by id: {}", idDoctor, e);
        } finally {
            MongoDbConnection.close();
        }
        return doctor;
    }

    @Override
    public int save(Doctor m) {
        return 0;
    }
    @Override
    public void update(Doctor m) {//not implemented
         }
    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }
}
