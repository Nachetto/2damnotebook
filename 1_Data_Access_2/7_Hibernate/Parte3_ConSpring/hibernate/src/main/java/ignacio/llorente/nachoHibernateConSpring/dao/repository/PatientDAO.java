package ignacio.llorente.nachoHibernateConSpring.dao.repository;

import ignacio.llorente.nachoHibernateConSpring.dao.model.Patient;

import java.util.List;

public interface PatientDAO {
    List<Patient> getAll();
    int save(Patient m);
    void update(Patient m);
    boolean delete(int id, boolean confirmation);
}
