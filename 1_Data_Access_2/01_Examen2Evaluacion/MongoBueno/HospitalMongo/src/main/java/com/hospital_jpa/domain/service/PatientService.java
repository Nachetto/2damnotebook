package com.hospital_jpa.domain.service;


import com.hospital_jpa.dao.interfaces.CredentialRepository;
import com.hospital_jpa.dao.interfaces.PatientRepository;
import com.hospital_jpa.dao.model.Credential;
import com.hospital_jpa.dao.model.Patient;
import com.hospital_jpa.domain.error.DUPLICATED_USERNAME;
import com.hospital_jpa.domain.error.FOREIGN_KEY_ERROR;
import com.hospital_jpa.domain.mappers.PatientMappers;
import com.hospital_jpa.domain.model.PatientUI;
import com.hospital_jpa.domain.utils.IdManager;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final CredentialRepository credentialRepository;
    private final IdManager idManager;
    private final PatientMappers mappers;

    public PatientService(PatientRepository patientRepository, CredentialRepository credentialRepository, IdManager idManager, PatientMappers mappers) {
        this.patientRepository = patientRepository;
        this.credentialRepository = credentialRepository;
        this.idManager = idManager;
        this.mappers = mappers;
    }

    public List<PatientUI> getAll() {
        List<Patient> patients = patientRepository.getAll();
        idManager.fillPatientIds(patients);
        return patients.stream().map(p -> mappers.toPatientUI(p, idManager.getPatientIntId(p.getId()))).toList();
    }

    public int addPatient(PatientUI patientUI) {
        ObjectId generatedId = patientRepository.save(mappers.toPatient(patientUI));
        if (generatedId != null) {
            try {
                credentialRepository.save(new Credential(patientUI.getUserName(), patientUI.getPassword(), generatedId));
            } catch (DUPLICATED_USERNAME e) {
                deletePatient(generatedId);
                throw new DUPLICATED_USERNAME();
            }
            idManager.addPatientId(generatedId);
        }
        return idManager.getPatientAutoIncrement() - 1;
    }

    public void updatePatient(PatientUI patientUI) {
        patientRepository.update(mappers.toPatient(patientUI, idManager.getPatientObjectId(patientUI.getId())));
    }

    public void deletePatient(int patientId, boolean confirmation) {
        ObjectId objectId = idManager.getPatientObjectId(patientId);
        credentialRepository.delete(objectId);
        patientRepository.delete(objectId, confirmation);
    }

    private void deletePatient(ObjectId patientId) {
        patientRepository.delete(patientId, true);
    }
}