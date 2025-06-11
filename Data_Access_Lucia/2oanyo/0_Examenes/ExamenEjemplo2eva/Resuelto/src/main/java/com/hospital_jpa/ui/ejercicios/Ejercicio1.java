package com.hospital_jpa.ui.ejercicios;

import com.hospital_jpa.dao.cargaDatos.CargaDatos;
import com.hospital_jpa.domain.model.hibernateModel.WeaponHibernate;
import com.hospital_jpa.domain.service.WeaponService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Ejercicio1 {
    public static void main(String[] args) {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        WeaponService weaponService = container.select(WeaponService.class).get();

        //this is
        WeaponHibernate weaponHibernate=new WeaponHibernate(2,"pistola","");


    }
}
