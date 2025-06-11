package com.exam.domain.service;

import com.exam.dao.model.hibernateModel.AnimalHibernate;
import com.exam.dao.Repository.repositoriesHibernate.AnimalRepository;
import jakarta.inject.Inject;

public class AnimalService {
    private final AnimalRepository repository;
    @Inject
    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public AnimalHibernate get(String name) {
        return repository.get(name);
    }
}
