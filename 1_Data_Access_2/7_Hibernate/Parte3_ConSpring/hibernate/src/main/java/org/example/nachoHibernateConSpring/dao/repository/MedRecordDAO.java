package org.example.nachoHibernateConSpring.dao.repository;

import org.example.nachoHibernateConSpring.dao.model.MedRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedRecordDAO extends JpaRepository<MedRecord,Integer>{
    List<MedRecord> findByPatientId(int patientId);
    void deleteById(int medRecordId);
}
