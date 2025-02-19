package com.hospitalcrud.dao.util;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.hospitalcrud.domain.model.MedRecordUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedRecordUIDeserializer extends JsonDeserializer<MedRecordUI> {

    @Override
    public MedRecordUI deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        int id = node.get("id").asInt();
        String description = node.get("description").asText();
        String date = node.get("date").asText();
        int idPatient = node.get("idPatient").asInt();
        int idDoctor = node.get("idDoctor").asInt();
        List<String> medications = new ArrayList<>();
        node.get("medications").forEach(med -> medications.add(med.asText()));

        return new MedRecordUI(id, description, date, idPatient, idDoctor, medications);
    }
}