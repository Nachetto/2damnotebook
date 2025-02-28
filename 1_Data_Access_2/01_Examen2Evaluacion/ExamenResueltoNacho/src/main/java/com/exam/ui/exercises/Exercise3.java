package com.exam.ui.exercises;

import com.exam.domain.service.AnimalVisitsService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Exercise3 {
    public static void main(String[] args) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        AnimalVisitsService service = container.select(AnimalVisitsService.class).get();
        service.saveVisits();

    }
}
