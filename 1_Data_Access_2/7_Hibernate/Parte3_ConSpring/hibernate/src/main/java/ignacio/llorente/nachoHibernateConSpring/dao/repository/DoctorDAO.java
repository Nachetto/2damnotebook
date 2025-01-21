package ignacio.llorente.nachoHibernateConSpring.dao.repository;

import ignacio.llorente.nachoHibernateConSpring.dao.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorDAO  extends JpaRepository<Doctor,Integer> {
}

