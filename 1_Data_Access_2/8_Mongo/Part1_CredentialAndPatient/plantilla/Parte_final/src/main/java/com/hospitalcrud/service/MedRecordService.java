package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.repository.hibernate.MedRecordRepository;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import com.hospitalcrud.domain.model.MedRecordUI;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MedRecordService {
    private final MedRecordRepository dao;
    private final MedicationService medicationService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private static final Map<Integer, ObjectId> idMapperList = new ConcurrentHashMap<>();

    public MedRecordService(MedRecordRepository dao, MedicationService medicationService, PatientService patientService, DoctorService doctorService) {
        this.dao = dao;
        this.medicationService = medicationService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public List<MedRecordUI> getMedRecords(int patientId) {
        List<MedRecord> medRecords = dao.get(patientId);
        AtomicInteger i = new AtomicInteger();
        List<MedRecordUI> finalMedRecords = new ArrayList<>();
        medRecords.forEach(m -> {
            int id = i.incrementAndGet();
            finalMedRecords.add(new MedRecordUI(id, m.getMedications(), m.getDoctorId(), m.getDate(), m.getDiagnosis()));
            idMapperList.put(id, m.getId());
        });
        return finalMedRecords;
    }

    public int add(MedRecordUI medRecordUI) {
        //add medications as well if everything is ok
        return  dao.save(medRecordUI.toMedRecord(patientService, doctorService, new ObjectId()));
    }

    public void update(MedRecordUI medRecordUI) {
        dao.update(medRecordUI.toMedRecord(patientService, doctorService));
    }

    public void delete(int medRecordId) {
        medicationService.delete(medRecordId);
        if (!dao.delete(medRecordId)) {
            throw new InternalServerErrorException("Error deleting med record");
        }
    }

    public boolean checkPatientMedRecords(int patientId) {
        return dao.get(patientId).isEmpty();
    }
}