package ignacio.llorente.nachoHibernateConSpring.service;

import ignacio.llorente.nachoHibernateConSpring.dao.model.Patient;
import ignacio.llorente.nachoHibernateConSpring.dao.repository.hibernate.CredentialRepository;
import ignacio.llorente.nachoHibernateConSpring.dao.repository.hibernate.PatientRepository;
import ignacio.llorente.nachoHibernateConSpring.domain.error.MedicalRecordException;
import ignacio.llorente.nachoHibernateConSpring.domain.error.UsernameDuplicatedException;
import ignacio.llorente.nachoHibernateConSpring.domain.model.PatientUI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository dao;
    private  final CredentialRepository credentialDAO;

    public PatientService(PatientRepository dao, CredentialRepository credentialDAO, CredentialService credentialService) {
        this.dao = dao;
        this.credentialDAO = credentialDAO;
    }

    public List<PatientUI> getPatients() {
        return dao.getAll()
                .stream()
                .map(Patient::toPatientUI).toList();
    }

    public int addPatient(PatientUI patientUI) {
        //check for duplicated username
        if (credentialDAO.validateUsername(patientUI.getUserName()))
            throw new UsernameDuplicatedException("Username duplicated, it already exists");

        int patientId = dao.save(patientUI.toPatient());

        if (patientId!=1) {
            throw new MedicalRecordException("Unexpected error saving patient, no pawtient was saved, rolling back...");
        }
        return patientId;
    }

    public void updatePatient(PatientUI patientUI) {
        dao.update(patientUI.toPatient());
    }

    public boolean delete(int patientId, boolean confirm) {
        //this way of making it makes it  so that it will always call all those deletes even after checking if there are records, but it is ok, it is a small amount of data
//        if (!confirm && !medRecordService.checkPatientMedRecords(patientId)) {
//                throw new MedicalRecordException("Patient has medical records, cannot delete.");
//        }
        return dao.delete(patientId, confirm);
    }

    public Patient getById(int id) {
        return dao.getById(id);
    }

}
