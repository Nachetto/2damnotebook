package com.hospitalcrud.dao.repository.hibernate;

import com.hospitalcrud.dao.connection.MongoDbConnection;
import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.repository.CredentialDAO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("mongodb")
public class CredentialRepository implements CredentialDAO {

    private final MongoCollection<Credential> collection;

    public CredentialRepository() {
        this.collection = MongoDbConnection.getDatabase()
                .getCollection("credentials", Credential.class);
    }


    public boolean validateUsername(String username) {
        Bson filter = Filters.eq("username", username);
        return collection.countDocuments(filter) > 0;
    }


    public boolean login(String username, String password) {
        Bson filter = Filters.and(
                Filters.eq("username", username),
                Filters.eq("password", password)
        );
        return collection.countDocuments(filter) > 0;
    }

    @Override
    public List<Credential> getAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public int save(Credential c) {
        collection.insertOne(c);
        return 1; // MongoDB no retorna IDs numéricos, ajustar según tu modelo
    }

    @Override
    public boolean delete(int id) {
        Bson filter = Filters.eq("_id", id);
        return collection.deleteOne(filter).getDeletedCount() > 0;
    }

    @Override
    public void update(Credential c) {
        Bson filter = Filters.eq("_id", c.getId());
        collection.replaceOne(filter, c);
    }
}