package com.hospital_jpa.dao.repositoriesMongo.old;

public class FactionRepository {
/*
    public List<Faction> getAll() {
        List<Faction> patients = new ArrayList<>();
        try (MongoUtil mongoUtil = new MongoUtil()) {
            var collection = mongoUtil.getDatabase().getCollection("factions");

            List<Document> documents = collection.find().into(new ArrayList<>());
            for (Document doc : documents) {
                Faction patient = gson.fromJson(doc.toJson(), Faction.class);
                //INFO: Json no pilla bien el id, por lo que se debe hacer manualmente
                // para que vaya inicializa private final Gson gson = GsonProvider.createGson();
                patient.setId(doc.getObjectId("_id"));
                patients.add(patient);
            }
        } catch (Exception e) {
            log.error("Error al traer los pacientes: {}", e.getMessage(), e);
        }
        return patients;
    }
    */
}
