package com.hospital_jpa.domain.service;


import com.hospital_jpa.dao.interfaces.PatientRepository;
import com.hospital_jpa.dao.interfaces.PaymentsRepository;
import com.hospital_jpa.dao.model.Credential;
import com.hospital_jpa.dao.model.Patient;
import com.hospital_jpa.dao.model.Payment;
import com.hospital_jpa.domain.model.PatientUI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PaymentsRepository paymentsRepository;

    public PatientService(PatientRepository patientRepository, PaymentsRepository paymentsRepository) {
        this.patientRepository = patientRepository;
        this.paymentsRepository = paymentsRepository;
    }

    public List<PatientUI> getPatients() {
        List<Patient> patients = patientRepository.getAll();
        List<PatientUI> patientsUI = new ArrayList<>();
        patients.forEach(p -> patientsUI.add(new PatientUI(p)));
        List<Payment> payments = paymentsRepository.getPaymentsByPatient();
        payments.forEach(p -> patientsUI.stream()
                .filter(patient -> patient.getId() == p.getPatient().getId())
                .findAny()
                .ifPresent(found -> found.setPaid(p.getAmount())));
        return patientsUI;
    }

    public int addPatient(PatientUI patientUI) {
        Patient patient = new Patient(patientUI.getId(), patientUI.getName(), patientUI.getBirthDate(),
                patientUI.getPhone(), new Credential(patientUI.getUserName(), patientUI.getPassword()));
        return patientRepository.save(patient);
    }

    public void updatePatient(PatientUI patientUI) {
        Patient patient = new Patient(patientUI.getId(), patientUI.getName(), patientUI.getBirthDate(), patientUI.getPhone());
        patientRepository.update(patient);
    }

    public void deletePatient(int patientId, boolean confirmation) {
        patientRepository.delete(patientId,confirmation);
    }
}
