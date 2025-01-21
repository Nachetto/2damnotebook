package ignacio.llorente.nachoHibernateConSpring.service;

import ignacio.llorente.nachoHibernateConSpring.dao.model.Medication;
import ignacio.llorente.nachoHibernateConSpring.dao.repository.MedicationDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {
    private final MedicationDAO dao;

    public MedicationService(MedicationDAO dao) {
        this.dao = dao;
    }

    public List<String> getMedications(int id) {//get medications for a medRecord
        return dao.get(id).stream().map(Medication::getMedicationName).toList();
    }

    public void delete(int medRecordId) {
        //delete payments as well
        dao.delete(medRecordId);
    }

    // not implemented for this exercise, created on client
}
