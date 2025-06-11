package com.hospital_jpa.dao.respositories;

import com.google.gson.Gson;
import com.hospital_jpa.dao.common.Constants;
import com.hospital_jpa.dao.model.Patient;
import com.hospital_jpa.domain.error.FOREIGN_KEY_ERROR;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
@Log4j2
public class PatientRepository implements com.hospital_jpa.dao.interfaces.PatientRepository {

    private final Gson gson;

    public PatientRepository(Gson gson) {
        this.gson = gson;
    }


    @Override
    public List<Patient> getAll() {
        try (MongoClient mongo = MongoClients.create(Constants.MONGODB_URL)) {
            MongoDatabase db = mongo.getDatabase(Constants.DB_NAME);
            MongoCollection<Document> collection = db.getCollection(Constants.PATIENTS);
            List<Patient> patients = new ArrayList<>();
            List<Document> documents = collection.find().into(new ArrayList<>());
            for (Document document : documents) {
                Patient patient = gson.fromJson(document.toJson(), Patient.class);
                patient.setId(document.getObjectId(com.hospital_jpa.common.Constants.ID));
                patients.add(patient);
            }
            return patients;
        }
    }

    @Override
    public ObjectId save(Patient patient) {
        try (MongoClient mongo = MongoClients.create(Constants.MONGODB_URL)) {
            MongoDatabase db = mongo.getDatabase(Constants.DB_NAME);
            MongoCollection<Document> patients = db.getCollection(Constants.PATIENTS);
            Patient p = Patient.builder().name(patient.getName())
                    .birthDate(patient.getBirthDate()).phone(patient.getPhone()).payments(patient.getPayments()).build();
            Document document = Document.parse(gson.toJson(p));
            patients.insertOne(document);
            return (ObjectId) document.get(com.hospital_jpa.common.Constants.ID);
        }
    }

    @Override
    public void update(Patient patient) {
        try (MongoClient mongo = MongoClients.create(Constants.MONGODB_URL)) {
            MongoDatabase db = mongo.getDatabase(Constants.DB_NAME);
            MongoCollection<Document> collection = db.getCollection(Constants.PATIENTS);
            Document filter = new Document(com.hospital_jpa.common.Constants.ID, patient.getId());
            Bson updates = Updates.combine(
                    Updates.set(com.hospital_jpa.common.Constants.NAME, patient.getName()),
                    Updates.set(com.hospital_jpa.common.Constants.BIRTH_DATE,patient.getBirthDate().toString()),
                    Updates.set(com.hospital_jpa.common.Constants.PHONE, patient.getPhone())
            );
            collection.updateOne(filter, updates);
        }
    }

    @Override
    public void delete(ObjectId patientId, boolean confirmation) {
        try (MongoClient mongo = MongoClients.create(Constants.MONGODB_URL)) {
            MongoDatabase db = mongo.getDatabase(Constants.DB_NAME);
            MongoCollection<Document> patients = db.getCollection(Constants.PATIENTS);
            MongoCollection<Document> medicalRecord = db.getCollection(Constants.MEDICAL_RECORDS);
            if (!confirmation) {
                if (medicalRecord.find(eq(com.hospital_jpa.common.Constants.PATIENT, patientId)).first() != null) {
                    throw new FOREIGN_KEY_ERROR();//hay medrecords asociados, throweo yo la exception
                }
            }
            else {
                medicalRecord.deleteMany(eq(com.hospital_jpa.common.Constants.PATIENT, patientId)).wasAcknowledged();
            }
            patients.deleteOne(eq(com.hospital_jpa.common.Constants.ID, patientId));
        }
    }
}
