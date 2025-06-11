package com.hospital_jpa.dao.respositories;

import com.hospital_jpa.dao.interfaces.DoctorsRepository;
import com.hospital_jpa.dao.model.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class DoctorRepositoryJPA implements DoctorsRepository {

    private final JPAUtil jpaUtil;

    public DoctorRepositoryJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = new ArrayList<>();

        try (EntityManager em = jpaUtil.getEntityManager()) {
            doctors = em.createNamedQuery("GET_ALL_DOCTORS", Doctor.class).getResultList();
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
        }
        return doctors;
    }
}
