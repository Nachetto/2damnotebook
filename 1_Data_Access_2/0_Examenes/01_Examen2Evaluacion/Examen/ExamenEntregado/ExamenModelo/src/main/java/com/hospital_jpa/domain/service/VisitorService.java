package com.hospital_jpa.domain.service;

import com.hospital_jpa.dao.model.hibernateModel.VisitorHibernate;
import com.hospital_jpa.dao.repositoriesHibernate.VisitorRepository;
import jakarta.inject.Inject;

public class VisitorService {
    private VisitorRepository visitorRepository;
    @Inject
    public VisitorService(VisitorRepository visitorRepository){
        this.visitorRepository=visitorRepository;
    }
    public int get(String name){
        //returns id from visitor name
        return visitorRepository.get(name);
    }
    public VisitorHibernate get(int id){
        return visitorRepository.get(id);
    }
}
