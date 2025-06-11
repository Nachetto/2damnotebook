package com.hospital_jpa.ui.exercises;

import com.hospital_jpa.dao.model.mongoModel.AnimalVisitsMongo;
import com.hospital_jpa.domain.service.AnimalVisitsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import java.util.Date;

public class Exercise3 {
    public static void main(String[] args) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        AnimalVisitsService service = container.select(AnimalVisitsService.class).get();
        service.saveVisits();

    }
}
