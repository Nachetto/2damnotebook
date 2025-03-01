package com.exam.dao.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

import java.util.Date;

public class GsonProvider {
    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter())
                .registerTypeAdapter(Date.class, new DateAdapter())
                .create();
    }
}