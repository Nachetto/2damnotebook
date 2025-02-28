package com.exam.domain.service;

import com.exam.dao.model.hibernateModel.VisitorHibernate;
import com.exam.dao.repositoriesHibernate.VisitorRepository;
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
