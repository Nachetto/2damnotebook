package com.exam.dao.cargaDatos;
/*
import com.google.gson.Gson;
import com.hospital_jpa.dao.model.hibernateModel.old.FactionHibernate;
import com.hospital_jpa.dao.model.hibernateModel.old.WeaponHibernate;
import com.hospital_jpa.dao.model.mongoModel.old.FactionMongo;
import com.hospital_jpa.dao.model.mongoModel.old.FactionsMongo;
import com.hospital_jpa.dao.model.mongoModel.old.WeaponMongo;
import com.hospital_jpa.dao.utils.JPAUtil;
import com.hospital_jpa.dao.utils.MongoUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.bson.Document;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class CargaDatos {

    private final Gson gson = new Gson();
    private final JPAUtil jpaUtil=new JPAUtil();


    public void cargarDatos() {
        List<FactionMongo> factionsList = new ArrayList<>();

        try (MongoUtil mongoUtil = new MongoUtil()) {

            var collection = mongoUtil.getDatabase().getCollection("factions");
            Document documento = collection.find().first();

            if (documento != null) {
                FactionsMongo factionsMongo = gson.fromJson(documento.toJson(), FactionsMongo.class);
                factionsList = factionsMongo.getFactions();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        guardarFactions(factionsList);
    }


    public void guardarFactions(List<FactionMongo> factionsList) {
        EntityTransaction tx = null;
        try (EntityManager em = jpaUtil.getEntityManager()) {
            tx = em.getTransaction();
            tx.begin();

            for (FactionMongo factionMongo : factionsList) {
                FactionHibernate factionHibernate = new FactionHibernate();
                factionHibernate.setFname(factionMongo.getName());
                factionHibernate.setContact(factionMongo.getContact());
                factionHibernate.setPlanet(factionMongo.getPlanet());
                factionHibernate.setNumberControlledSystems(factionMongo.getNumberCS());
                factionHibernate.setDateLastPurchase(factionMongo.getDateLastPurchase());

                List<WeaponHibernate> weaponHibernates = new ArrayList<>();
                if (factionMongo.getWeaponMongos() != null) {
                    for (WeaponMongo weaponMongo : factionMongo.getWeaponMongos()) {
                        WeaponHibernate weaponEntity = new WeaponHibernate();
                        weaponEntity.setWname(weaponMongo.getName());
                        weaponEntity.setWprice(weaponMongo.getPrice());
                        weaponHibernates.add(weaponEntity);
                    }
                }

                factionHibernate.setWeapons(weaponHibernates);
                em.persist(factionHibernate);
            }

            tx.commit();
        } catch (ConstraintViolationException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
}
*/