package org.example.nachoHibernateConSpring.dao.repository;

import org.example.nachoHibernateConSpring.dao.model.MedRecord;
import org.example.nachoHibernateConSpring.dao.model.Medication;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface MedicationDAO extends JpaRepository<Medication,Integer>{
    @Modifying
    @Transactional(noRollbackFor = DataIntegrityViolationException.class)
    void deleteByMedRecord(MedRecord medRecord);
}