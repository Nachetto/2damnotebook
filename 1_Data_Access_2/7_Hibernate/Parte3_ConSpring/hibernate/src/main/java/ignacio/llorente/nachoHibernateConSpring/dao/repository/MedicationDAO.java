package ignacio.llorente.nachoHibernateConSpring.dao.repository;

import ignacio.llorente.nachoHibernateConSpring.dao.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationDAO extends JpaRepository<Medication,Integer>{}