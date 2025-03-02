package com.exam.ui.exercises;

import com.exam.dao.model.mongoModel.AnimalMongo;
import com.exam.domain.service.AnimalVisitsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Exercise3 {
    public static void main(String[] args) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        AnimalVisitsService service = container.select(AnimalVisitsService.class).get();
        service.saveVisits(); //guarda las visitas desde hibernate hasta mongo
        //para nemo, el dia que fue visitado meterle un update a la visiita y añadirle un nuevo animal
        service.updateNewAnimalToVisit(service.getVisitFromAnimalName("Nemo"), new AnimalMongo("Deer", "Forest"));

    }
}
