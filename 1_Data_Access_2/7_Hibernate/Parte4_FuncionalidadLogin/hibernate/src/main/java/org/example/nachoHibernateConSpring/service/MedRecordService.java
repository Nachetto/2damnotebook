package org.example.nachoHibernateConSpring.service;

import org.example.nachoHibernateConSpring.dao.model.MedRecord;
import org.example.nachoHibernateConSpring.dao.model.Medication;
import org.example.nachoHibernateConSpring.dao.model.Session;
import org.example.nachoHibernateConSpring.dao.repository.MedRecordDAO;
import org.example.nachoHibernateConSpring.dao.repository.SessionDao;
import org.example.nachoHibernateConSpring.domain.error.BadRequestException;
import org.example.nachoHibernateConSpring.domain.model.MedRecordUI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedRecordService {
    private final MedRecordDAO dao;
    private final MedicationService medicationService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final SessionDao sessionDao;

    public MedRecordService(MedRecordDAO dao, MedicationService medicationService, PatientService patientService, DoctorService doctorService, SessionDao sessionDao) {
        this.dao = dao;
        this.medicationService = medicationService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.sessionDao = sessionDao;
    }

    @Transactional
    public int add(MedRecordUI medRecordUI) {
        Session session = sessionDao.load();
        if (session == null) throw new BadRequestException("No active session");

        if (session.getUserType().equals("doctor") && medRecordUI.getIdDoctor() != session.getUserId()) {
            throw new BadRequestException("Doctor can only add records assigned to themselves");
        }else if (session.getUserType().equals("patient")){
            throw new BadRequestException("Patient cannot add medical records");
        }

        MedRecord medRecord = medRecordUI.toMedRecord(patientService, doctorService);

        List<Medication> medications = medRecordUI.getMedications().stream()
                .map(medicationName -> new Medication(medicationName, medRecord))
                .collect(Collectors.toList());
        medRecord.setMedications(medications);

        MedRecord savedMedRecord = dao.save(medRecord);
        return savedMedRecord.getId();
    }

    @Transactional
    public void update(MedRecordUI medRecordUI) {
        Session session = sessionDao.load();
        if (session == null) throw new BadRequestException("No active session");

        if (session.getUserType().equals("doctor")
                && dao.findById(medRecordUI.getId()).get().getDoctor().getId()!=session.getUserId()){
            throw new BadRequestException("Doctor cannot update medical records that are not assigned to them"); //this should never happen, but just in case
        }

        MedRecord existingMedRecord = dao.findById(medRecordUI.getId())
                .orElseThrow(() -> new IllegalArgumentException("MedRecord not found"));

        existingMedRecord.setDiagnosis(medRecordUI.getDescription());
        existingMedRecord.setDate(LocalDate.parse(medRecordUI.getDate()));

        existingMedRecord.getMedications().clear();
        List<Medication> medications = medRecordUI.getMedications().stream()
                .map(medicationName -> new Medication(medicationName, existingMedRecord))
                .toList();
        existingMedRecord.getMedications().addAll(medications);

        dao.save(existingMedRecord);
    }

    public void delete(int medRecordId) {
        Session session = sessionDao.load();
        if (session == null) throw new BadRequestException("No active session");

        if (session.getUserType().equals("doctor")
                && dao.findById(medRecordId).get().getDoctor().getId()!=session.getUserId()){
            throw new BadRequestException("Doctor cannot delete medical records that are not assigned to them");//this should never happen, but just in case
        }else if (session.getUserType().equals("patient")){
            throw new BadRequestException("Patient cannot delete medical records");
        }
        
        medicationService.delete(medRecordId);
        dao.deleteById(medRecordId);
    }

    public List<MedRecordUI> getMedRecords(int patientId) {
        List<MedRecord> medRecords = dao.findByPatientId(patientId);
        Session session = sessionDao.load();
        if (session == null) throw new BadRequestException("No active session");
        if (session.getUserType().equals("doctor")) {
            medRecords = medRecords.stream()
                    .filter(medRecord -> medRecord.getDoctor().getId() == session.getUserId())
                    .toList();
        }

        return medRecords.stream().map(MedRecord::toMedRecordUI).toList();
    }

    public boolean checkPatientMedRecords(int patientId) {
        return dao.findByPatientId(patientId).isEmpty();
    }
}