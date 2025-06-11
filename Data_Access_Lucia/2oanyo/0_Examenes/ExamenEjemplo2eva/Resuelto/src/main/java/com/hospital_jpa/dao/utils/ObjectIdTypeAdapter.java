package com.hospital_jpa.dao.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdTypeAdapter extends TypeAdapter<ObjectId> {

    @Override
    public void write(JsonWriter out, ObjectId value) throws IOException {
        out.value(value == null ? null : value.toHexString());
    }

    @Override
    public ObjectId read(JsonReader in) throws IOException {
        return in.peek() == com.google.gson.stream.JsonToken.NULL ? null : new ObjectId(in.nextString());
    }
}