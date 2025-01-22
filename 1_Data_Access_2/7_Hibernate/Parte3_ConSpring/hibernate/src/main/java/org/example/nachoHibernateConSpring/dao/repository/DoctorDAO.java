package org.example.nachoHibernateConSpring.dao.repository;

import org.example.nachoHibernateConSpring.dao.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DoctorDAO  extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByUsername(String username);
    List<Doctor> findByUsernameAndPassword(String username, String password);
}

