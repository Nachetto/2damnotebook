package com.exam.dao.utils.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

import static com.exam.dao.utils.Constantes.CONNECTION_STRING;
import static com.exam.dao.utils.Constantes.DATABASE_NAME;


public class MongoUtil implements AutoCloseable {

    private final MongoClient mongoClient;
    @Getter
    private final MongoDatabase database;

    public MongoUtil() {
        this.mongoClient = MongoClients.create(CONNECTION_STRING);
        this.database = mongoClient.getDatabase(DATABASE_NAME);
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}
