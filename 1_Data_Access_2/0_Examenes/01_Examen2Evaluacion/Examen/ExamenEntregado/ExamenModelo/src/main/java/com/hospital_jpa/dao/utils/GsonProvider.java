package com.hospital_jpa.dao.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

public class GsonProvider {
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter())
                .create();
    }
}