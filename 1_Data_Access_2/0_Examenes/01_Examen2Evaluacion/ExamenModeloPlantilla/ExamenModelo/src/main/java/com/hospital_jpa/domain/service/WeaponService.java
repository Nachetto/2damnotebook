package com.hospital_jpa.domain.service;


import com.hospital_jpa.dao.repositoriesHibernate.WeaponRepository;
import com.hospital_jpa.domain.model.hibernateModel.WeaponHibernate;
import jakarta.inject.Inject;

public class WeaponService {
    private final WeaponRepository weaponRepository;

    @Inject
    public WeaponService(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    public int save(WeaponHibernate weaponHibernate) {
        return weaponRepository.save(weaponHibernate);
    }

    public void delete(int id) {
        weaponRepository.delete(id);
    }
}
