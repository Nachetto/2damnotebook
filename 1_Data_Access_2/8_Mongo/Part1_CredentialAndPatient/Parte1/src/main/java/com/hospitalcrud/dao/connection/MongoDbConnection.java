package com.hospitalcrud.dao.connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnection {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private static final String CONNECTION_STRING = "mongodb://informatica.iesquevedo.es:2323";
    private static final String DB_NAME = "IgnacioLlorente_hospital";

    private MongoDbConnection() {}

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DB_NAME);
        }
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}