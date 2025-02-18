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
        if (credentialDAO.validateUsername(patientUI.getUserName()))
            throw new UsernameDuplicatedException("Username duplicated, it already exists");
        int patientId = dao.save(patientUI.toPatient(new ObjectId()));
        if (patientId!=1) {
            throw new MedicalRecordException("Unexpected error saving patient, no pawtient was saved, rolling back...");
        }
        return patientId;
    }

    public void updatePatient(PatientUI patientUI) {
        dao.update(patientUI.toPatient(idMapperList.get(patientUI.getId())));
    }

    public boolean delete(int patientId, boolean confirm) {
        ObjectId objectId = idMapperList.get(patientId);
        if (objectId == null) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        int deletedCount = dao.delete(objectId, confirm);
        return deletedCount > 0;
    }

    public Patient getById(int id) {
        ObjectId objectId = idMapperList.get(id);
        if (objectId == null) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        return dao.getById(objectId);
    }

}
