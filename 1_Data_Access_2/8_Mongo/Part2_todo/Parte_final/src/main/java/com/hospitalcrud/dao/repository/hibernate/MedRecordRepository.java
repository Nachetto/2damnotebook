package com.hospitalcrud.dao.repository.hibernate;

import com.google.gson.Gson;
import com.hospitalcrud.dao.connection.MongoDbConnection;
import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.MedRecordDAO;
import com.hospitalcrud.dao.util.GsonCreator;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


@Repository
@Profile("mongodb")
@Log4j2
@Data
public class MedRecordRepository implements MedRecordDAO {
    private static final String COLLECTION_NAME = "MedRecord";

    @Override
    public List<MedRecord> getAll() {
        List<MedRecord> medRecords = new ArrayList<>();
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            for (Document doc : collection.find()) {
                Gson gson = GsonCreator.createGson();
                MedRecord medRecord = gson.fromJson(doc.toJson(), MedRecord.class);
                medRecord.setId(doc.getObjectId("_id"));
                medRecord.setDoctorId(doc.getObjectId("doctorId"));
                medRecord.setPatientId(doc.getObjectId("patientId"));
                Date date = doc.getDate("date");
                if (date != null) {
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    medRecord.setDate(localDate);
                }
                medRecords.add(medRecord);
            }

        } catch (Exception e) {
            log.error("Error getting all med records", e);
        } finally {
            MongoDbConnection.close();
        }
        return medRecords;
    }

    @Override
    public List<MedRecord> get(ObjectId patientId) {
        List<MedRecord> medRecords = new ArrayList<>();
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            for (Document doc : collection.find(eq("patientId", patientId))) {
                Gson gson = GsonCreator.createGson();
                MedRecord medRecord = gson.fromJson(doc.toJson(), MedRecord.class);
                medRecord.setId(doc.getObjectId("_id"));
                medRecord.setDoctorId(doc.getObjectId("doctorId"));
                medRecord.setPatientId(doc.getObjectId("patientId"));
                Date date = doc.getDate("date");
                if (date != null) {
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    medRecord.setDate(localDate);
                }
                medRecords.add(medRecord);
            }

        } catch (Exception e) {
            log.error("Error getting med records for patientId: {}", patientId, e);
        } finally {
            MongoDbConnection.close();
        }
        return medRecords;
    }

    @Override
    public int save(MedRecord medRecord) {
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            Gson gson = GsonCreator.createGson();

            String json = gson.toJson(medRecord);
            Document document = Document.parse(json);

            document.put("patientId", medRecord.getPatientId());
            document.put("doctorId", medRecord.getDoctorId());

            //convert date to Date
            LocalDate date = medRecord.getDate();
            document.put("date", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));


            document.remove("_id");
            collection.insertOne(document);
            return document.getObjectId("_id").getTimestamp();
        } catch (Exception e) {
            log.error("Error saving medication: {}", medRecord, e);
            return 0;
        } finally {
            MongoDbConnection.close();
        }
    }

    @Override
    public void update(MedRecord medRecord) {
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            List<Document> medicationDocs = new ArrayList<>();
            for (Medication medication : medRecord.getMedications()) {
                Document medDoc = new Document("name", medication.getName());
                medicationDocs.add(medDoc);
            }

            LocalDate date = medRecord.getDate();

            Document updateFields = new Document()
                    .append("date", Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .append("patientId", medRecord.getPatientId())
                    .append("doctorId", medRecord.getDoctorId())
                    .append("diagnosis", medRecord.getDiagnosis())
                    .append("medications", medicationDocs);

            collection.updateOne(
                    eq("_id", medRecord.getId()),
                    new Document("$set", updateFields)
            );
        } catch (Exception e) {
            log.error("Error updating medication: {}", medRecord, e);
        } finally {
            MongoDbConnection.close();
        }
    }

    @Override
    public boolean delete(ObjectId id) {
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            long deletedCount = collection.deleteOne(eq("_id", id)).getDeletedCount();
            return deletedCount > 0;
        } catch (Exception e) {
            log.error("Error deleting medication: {}", id, e);
            return false;
        } finally {
            MongoDbConnection.close();
        }
    }
}
