package com.hospital_jpa.dao.respositories;

import com.google.gson.Gson;
import com.hospital_jpa.dao.common.Constants;
import com.hospital_jpa.dao.interfaces.DoctorsRepository;
import com.hospital_jpa.dao.model.Doctor;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
@RequiredArgsConstructor
public class DoctorRepository implements DoctorsRepository {

    private final Gson gson;

    @Override
    public List<Doctor> getAll() {
        try (MongoClient mongo = MongoClients.create(Constants.MONGODB_URL)) {
            MongoDatabase db = mongo.getDatabase(Constants.DB_NAME);
            MongoCollection<Document> est = db.getCollection(Constants.DOCTORS);
            List<Doctor> doctors = new ArrayList<>();
            List<Document> documents = est.find().into(new ArrayList<>());
            for (Document document : documents) {
                Doctor doctor = gson.fromJson(document.toJson(), Doctor.class);
                doctor.setId(document.getObjectId(com.hospital_jpa.common.Constants.ID));
                doctors.add(doctor);
            }
            return doctors;
        }
    }
}
