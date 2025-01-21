package ignacio.llorente.nachoHibernateConSpring.dao.repository;

import ignacio.llorente.nachoHibernateConSpring.dao.model.MedRecord;

import java.util.List;

public interface MedRecordDAO {
    List<MedRecord> getAll();
    List<MedRecord> get(int patientId);
    int save(MedRecord m);
    void update(MedRecord m);
    boolean delete(int id);

}
