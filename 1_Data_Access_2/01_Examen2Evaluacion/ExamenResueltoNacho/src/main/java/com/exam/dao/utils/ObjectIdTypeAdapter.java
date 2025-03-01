package com.exam.dao.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.lang.reflect.Type;

public class ObjectIdTypeAdapter extends TypeAdapter<ObjectId>  implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId>{

    @Override
    public void write(JsonWriter out, ObjectId value) throws IOException {
        out.value(value == null ? null : value.toHexString());
    }

    @Override
    public ObjectId read(JsonReader in) throws IOException {
        return in.peek() == com.google.gson.stream.JsonToken.NULL ? null : new ObjectId(in.nextString());
    }

    @Override
    public JsonElement serialize(ObjectId src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toHexString());
    }

    @Override
    public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new ObjectId(json.getAsString());
    }

}