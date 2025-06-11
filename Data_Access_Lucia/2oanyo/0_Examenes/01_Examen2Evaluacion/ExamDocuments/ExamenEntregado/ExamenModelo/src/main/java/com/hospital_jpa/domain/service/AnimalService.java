package com.hospital_jpa.domain.service;

import com.hospital_jpa.dao.model.hibernateModel.AnimalHibernate;
import com.hospital_jpa.dao.repositoriesHibernate.AnimalRepository;
import jakarta.inject.Inject;

public class AnimalService {
    private final AnimalRepository repository;
    @Inject
    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public AnimalHibernate get(int habitatID) {
        return repository.get(habitatID);
    }
}
