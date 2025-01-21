package ignacio.llorente.nachoHibernateConSpring.service;

import ignacio.llorente.nachoHibernateConSpring.dao.model.Doctor;
import ignacio.llorente.nachoHibernateConSpring.dao.repository.DoctorDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorDAO dao;

    public DoctorService(DoctorDAO dao) {
        this.dao = dao;
    }

    public List<Doctor> getDoctors() {
        return dao.getAll();
    }


    public Doctor getDoctor(int doctorId) {
        return dao.getById(doctorId);
    }
}