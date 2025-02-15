package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.repository.hibernate.CredentialRepository;
import com.hospitalcrud.dao.repository.hibernate.PatientRepository;
import com.hospitalcrud.domain.error.MedicalRecordException;
import com.hospitalcrud.domain.error.UsernameDuplicatedException;
import com.hospitalcrud.domain.model.PatientUI;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PatientService {
    private final PatientRepository dao;
    private  final CredentialRepository credentialDAO;
    private static final Map<Integer, ObjectId> idMapperList = new ConcurrentHashMap<>();

    public PatientService(PatientRepository dao, CredentialRepository credentialDAO, CredentialService credentialService) {
        this.dao = dao;
        this.credentialDAO = credentialDAO;
    }

    public List<PatientUI> getPatients() {
        List<Patient> patients = dao.getAll();
        AtomicInteger i = new AtomicInteger();
        List<PatientUI> finalPatients = new ArrayList<>();
        patients.forEach(p -> {
            int id = i.incrementAndGet();
            finalPatients.add(new PatientUI(id,p.getName(),p.getBirthDate(),p.getPhone(),0,null,null));
            idMapperList.put(id, p.getId());
        });
        return finalPatients;
    }

    public int addPatient(PatientUI patientUI) {
        //check for duplicated username
        if (credentialDAO.validateUsername(patientUI.getUserName()))
            throw new UsernameDuplicatedException("Username duplicated, it already exists");

        int patientId = dao.save(patientUI.toPatient(idMapperList.get(patientUI.getId())));

        if (patientId!=1) {
            throw new MedicalRecordException("Unexpected error saving patient, no pawtient was saved, rolling back...");
        }
        return patientId;
    }

    public void updatePatient(PatientUI patientUI) {
        dao.update(patientUI.toPatient(idMapperList.get(patientUI.getId())));
    }

    public boolean delete(int patientId, boolean confirm) {
        return dao.delete(patientId, confirm);
    }

    public Patient getById(int id) {
        return dao.getById(id);
    }

}
