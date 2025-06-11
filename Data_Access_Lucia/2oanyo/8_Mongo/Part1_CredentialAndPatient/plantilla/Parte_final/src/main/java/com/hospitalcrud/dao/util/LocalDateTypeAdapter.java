package com.hospitalcrud.dao.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        out.value(value != null ? value.format(formatter) : null);
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.BEGIN_OBJECT) {
            in.beginObject();
            LocalDate date = null;
            while (in.hasNext()) {
                String name = in.nextName();
                if ("date".equals(name)) {
                    date = LocalDate.parse(in.nextString(), formatter);
                } else {
                    in.skipValue();
                }
            }
            in.endObject();
            return date;
        } else {
            return LocalDate.parse(in.nextString(), formatter);
        }
    }
}