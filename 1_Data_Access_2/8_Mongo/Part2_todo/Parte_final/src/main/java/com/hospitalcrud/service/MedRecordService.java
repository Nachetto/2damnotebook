package com.hospitalcrud.service;

import com.hospitalcrud.dao.model.MedRecord;
import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.hibernate.MedRecordRepository;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import com.hospitalcrud.domain.model.MedRecordUI;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MedRecordService {
    private final MedRecordRepository dao;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private static  Map<Integer, ObjectId> idMapperList ;

    public MedRecordService(MedRecordRepository dao, PatientService patientService, DoctorService doctorService) {
        this.dao = dao;
        this.patientService = patientService;
        this.doctorService = doctorService;
        idMapperList = new ConcurrentHashMap<>();
    }

    public List<MedRecordUI> getMedRecords(int patientId) {
        ObjectId patientObjectId = patientService.getPatientObjectId(patientId);
        List<MedRecord> medRecords = dao.get(patientObjectId);

        List<MedRecordUI> finalMedRecords = new ArrayList<>();
        idMapperList.clear();

        AtomicInteger i = new AtomicInteger();

        medRecords.forEach(medRecord -> {
            int id = i.incrementAndGet();

            int doctorId;
            try {
                doctorId = doctorService.getDoctorId(medRecord.getDoctorId());
            } catch (IllegalArgumentException e) {
                doctorId = doctorService.saveDoctorObject(medRecord.getDoctorId());
            }

            String date;
            try {
                date = medRecord.getDate().toString();
            } catch (NullPointerException e) {
                date = "";
            }

            List<String> medications = medRecord.getMedications().stream()
                    .map(Medication::getName).toList();

            MedRecordUI ui = new MedRecordUI(
                    id,
                    medRecord.getDiagnosis(),
                    date,
                    patientId,
                    doctorId,
                    medications
            );
            finalMedRecords.add(ui);
            idMapperList.put(id, medRecord.getId());
        });
        return finalMedRecords;
    }

    public ObjectId getMedRecordObjectId(int id) {
        ObjectId  objectId = idMapperList.get(id);
        if (objectId == null) {
            throw new IllegalArgumentException("Invalid Medical Record ID");
        }
        return objectId;
    }

    public int add(MedRecordUI medRecordUI) {
        //add medications as well if everything is ok
        MedRecord medRecord = medRecordUI.toMedRecord(patientService, doctorService, new ObjectId());
        medRecord.setDoctorId(doctorService.getDoctorObjectId(medRecordUI.getIdDoctor()));
        medRecord.setPatientId(patientService.getPatientObjectId(medRecordUI.getIdPatient()));
        return  dao.save(medRecord);
    }

    public void update(MedRecordUI medRecordUI) {
        dao.update(medRecordUI.toMedRecord(patientService, doctorService, getMedRecordObjectId(medRecordUI.getId())));
    }

    public void delete(int medRecordId) {
        if (!dao.delete(getMedRecordObjectId(medRecordId))) {
            throw new InternalServerErrorException("Error deleting med record");
        }
    }

    public boolean checkPatientMedRecords(int patientId) {
        return dao.get(patientService.getPatientObjectId(patientId)).isEmpty();
    }
}