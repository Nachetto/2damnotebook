package org.example.nachoHibernateConSpring.dao.repository;

import org.example.nachoHibernateConSpring.dao.model.MedRecord;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MedRecordDAO extends JpaRepository<MedRecord,Integer>{
    List<MedRecord> findByPatientId(int patientId);
    @Modifying
    @Transactional(noRollbackFor = DataIntegrityViolationException.class)
    void deleteById(int medRecordId);
}
