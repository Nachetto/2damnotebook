package com.hospital_jpa.di;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdTypeAdapter extends TypeAdapter<ObjectId> {
    @Override
    public void write(JsonWriter out, ObjectId value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("$oid");
        out.value(value.toHexString());
        out.endObject();
    }

    @Override
    public ObjectId read(JsonReader in) throws IOException {
        in.beginObject();
        in.nextName(); // Expect "$oid"
        String hexString = in.nextString();
        in.endObject();
        return new ObjectId(hexString);
    }
}