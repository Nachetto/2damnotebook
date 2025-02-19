package com.hospitalcrud.dao.repository.hibernate;


import com.google.gson.Gson;
import com.hospitalcrud.dao.connection.MongoDbConnection;
import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repository.CredentialDAO;
import com.hospitalcrud.dao.util.GsonCreator;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Profile("mongodb")
@Log4j2
@Data
@Repository
public class CredentialRepository implements CredentialDAO {
    private static final String COLLECTION_NAME = "Credential";

    public boolean validateUsername(String username) {
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("username", username);
            long count = collection.countDocuments(query);

            return count > 0;
        } catch (Exception e) {
            log.error("Error validating username: {}", username, e);
            return false;
        } finally {
            MongoDbConnection.close();
        }
    }

    public boolean login(String username, String password) {
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("username", username).append("password", password);
            long count = collection.countDocuments(query);

            return count > 0;
        } catch (Exception e) {
            log.error("Error during login for username: {}", username, e);
            return false;
        } finally {
            MongoDbConnection.close();
        }
    }

    @Override
    public List<Credential> getAll() {
        List<Credential> credentials = new ArrayList<>();
        try {
            MongoDatabase database = MongoDbConnection.getDatabase();
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            for (Document doc : collection.find()) {
                Gson gson = GsonCreator.createGson();
                Credential credential = gson.fromJson(doc.toJson(), Credential.class);
                credential.setId(doc.getObjectId("_id"));
                credentials.add(credential);
            }

        } catch (Exception e) {
            log.error("Error getting all credentials", e);
        } finally {
            MongoDbConnection.close();
        }
        return credentials;
    }



    @Override
    public int save(Credential c) {
        //not used here
        return 0;}
    @Override
    public boolean delete(int id) {
        //not used here
        return false;
    }
    @Override
    public void update(Credential c) {
        //not used here
    }
}
