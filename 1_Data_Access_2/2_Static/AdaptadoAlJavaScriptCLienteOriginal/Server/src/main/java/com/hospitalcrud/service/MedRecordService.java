package com.hospitalcrud.service;


import com.hospitalcrud.dao.repository.staticDAO.MedRecordRepository;
import com.hospitalcrud.domain.model.MedRecordUI;

import java.util.List;
import java.util.stream.Collectors;
public class MedRecordService {
    private final MedRecordRepository dao;
    //llamar al DAO inyectado
    public MedRecordService() {
        this.dao = new MedRecordRepository();
    }

    public List<MedRecordUI> getAll() {
        return dao.getAll().stream().map(m -> m.toMedRecordUI()).collect(Collectors.toList());
    }

    public int add(MedRecordUI medRecordUI) {
        return dao.save(medRecordUI.toMedRecord());
    }

    public void update(MedRecordUI medRecordUI) {
        dao.update(medRecordUI.toMedRecord());
    }

    public void delete(int medRecordId) {
        dao.delete(medRecordId, true);
    }

    public List<MedRecordUI> getMedRecords(int patientId) {
        return dao.get(patientId).stream().map(m -> m.toMedRecordUI()).collect(Collectors.toList());
    }

    public boolean checkPatientMedRecords(int patientId) {
        return dao.get(patientId).isEmpty();
    }
}