package com.hospitalcrud.dao.repository.hibernate;


import com.hospitalcrud.dao.connection.JPAUtil;
import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.dao.repository.DoctorDAO;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("hibernate")
@Log4j2
public class DoctorRepository implements DoctorDAO {
    private final JPAUtil jpautil;
    private EntityManager em;

    public DoctorRepository(JPAUtil jpautil) {
        this.jpautil = jpautil;
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> list;
        try {
            em = jpautil.getEntityManager();
            list = em.createNamedQuery("Doctor.getAll", Doctor.class)
                    .getResultList();
        }catch (Exception e) {
            log.error("Error getting all doctors", e);
            return List.of();
        }
        finally {
            if (em != null) em.close();
        }
        return list;
    }

    @Override
    public int save(Doctor m) {
        return 0;
    }

    @Override
    public void update(Doctor m) {

    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }
}
