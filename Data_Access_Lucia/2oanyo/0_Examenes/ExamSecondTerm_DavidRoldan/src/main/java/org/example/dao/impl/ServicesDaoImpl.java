package org.example.dao.impl;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.example.dao.ServicesDAO;
import org.example.dao.SubscriptionsDAO;
import org.example.model.ServicesEntity;
import org.example.model.SubscriptionsEntity;
import org.example.model.errors.GymError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServicesDaoImpl implements ServicesDAO {
    private JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public ServicesDaoImpl(JPAUtil jpaUtil){
        this.jpaUtil = jpaUtil;
    }


    @Override
    public Either<GymError, ServicesEntity> get(String name) {
        Either<GymError, ServicesEntity> result = null;
        em = jpaUtil.getEntityManager();
        try{
            List<ServicesEntity> servicesEntities = em.createNamedQuery("selectServiceByName")
                    .setParameter("serviceName", name)
                    .getResultList();
            if (servicesEntities.isEmpty()){
                result = Either.left(new GymError("No service"));
            } else {
                result = Either.right(servicesEntities.get(0));
            }
        }catch (Exception e){
            result = Either.left(new GymError("Error  subscripciones"));
        } finally {
            if (em != null) em.close();
        }
        return result;
    }
}
