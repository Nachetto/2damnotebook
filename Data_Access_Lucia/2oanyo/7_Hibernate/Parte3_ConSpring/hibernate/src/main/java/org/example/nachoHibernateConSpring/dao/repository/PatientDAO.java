package org.example.nachoHibernateConSpring.dao.repository;

import org.example.nachoHibernateConSpring.dao.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientDAO  extends JpaRepository<Patient, Integer> {
}
