package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.Patient;
import com.hospitalcrud.dao.model.Payment;
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
    private static Map<Integer, ObjectId> idMapperList ;

    public PatientService(PatientRepository dao, CredentialRepository credentialDAO) {
        this.dao = dao;
        this.credentialDAO = credentialDAO;
        idMapperList = new ConcurrentHashMap<>();
    }

    public List<PatientUI> getPatients() {
        List<Patient> patients = dao.getAll();
        AtomicInteger i = new AtomicInteger();
        List<PatientUI> finalPatients = new ArrayList<>();
        patients.forEach(p -> {
            int id = i.incrementAndGet();
            finalPatients.add(new PatientUI(
                    id,
                    p.getName(),
                    p.getBirthDate(),
                    p.getPhone(),
                    totalPatientAmmountPaid(p),
                    null,
                    null));
            idMapperList.put(id, p.getId());
        });
        return finalPatients;
    }

    private int totalPatientAmmountPaid(Patient patients) {
        return patients.getPayments().stream().mapToInt(Payment::getAmount).sum();
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
        ObjectId objectId = getPatientObjectId(patientUI.getId());
        dao.update(patientUI.toPatient(objectId));
    }

    public boolean delete(int patientId, boolean confirm) {
        ObjectId objectId = getPatientObjectId(patientId);
        int deletedCount = dao.delete(objectId, confirm);
        return deletedCount > 0;
    }

    public Patient getById(int patientId) {
        ObjectId objectId = getPatientObjectId(patientId);
        return dao.getById(objectId);
    }



    public ObjectId getPatientObjectId(int patientId) {
        System.out.printf(idMapperList.toString());
        ObjectId objectId = idMapperList.get(patientId);
        if (objectId == null) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        return objectId;
    }
}
