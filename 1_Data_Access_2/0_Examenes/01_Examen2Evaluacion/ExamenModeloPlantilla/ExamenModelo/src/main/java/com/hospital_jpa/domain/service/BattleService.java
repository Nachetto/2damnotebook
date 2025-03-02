package com.hospital_jpa.domain.service;


import com.hospital_jpa.dao.repositoriesHibernate.BattleRepository;

import com.hospital_jpa.domain.model.hibernateModel.BattleHibernate;
import jakarta.inject.Inject;

import java.util.List;


public class BattleService {
    private final BattleRepository battleRepository;

    @Inject
    public BattleService(BattleRepository battleRepository) {
        this.battleRepository = battleRepository;
    }

    public List<BattleHibernate> getAll(){
        return battleRepository.getAll();
    }
}
