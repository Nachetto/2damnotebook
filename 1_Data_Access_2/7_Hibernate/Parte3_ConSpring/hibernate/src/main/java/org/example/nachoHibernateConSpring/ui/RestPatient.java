package org.example.nachoHibernateConSpring.ui;

import org.example.nachoHibernateConSpring.domain.error.MedicalRecordException;
import org.example.nachoHibernateConSpring.domain.model.MedRecordUI;
import org.example.nachoHibernateConSpring.domain.model.PatientUI;
import org.example.nachoHibernateConSpring.service.MedRecordService;
import org.example.nachoHibernateConSpring.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin(origins = "http://127.0.0.1:5500")

@RequestMapping("/patients")
public class RestPatient {

    private final PatientService patientService;
    private final MedRecordService medRecordService;

    public RestPatient(PatientService patientService, MedRecordService medRecordService) {
        this.patientService = patientService;
        this.medRecordService = medRecordService;
    }

    @GetMapping
    public List<PatientUI> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping
    @RequestMapping("/{patientId}/medRecords")
    public List<MedRecordUI> getPatientMedRecords(@PathVariable int patientId) {
        return medRecordService.getMedRecords(patientId);
    }

    @PostMapping
    public int addPatient(@RequestBody PatientUI patientUI) {
        return patientService.addPatient(patientUI);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePatient(@RequestBody PatientUI patientUI) {
        patientService.updatePatient(patientUI);
    }

    @RequestMapping("/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deletePatient(@PathVariable int patientId, @RequestParam boolean confirm) {
        if (!confirm && !medRecordService.checkPatientMedRecords(patientId)) {
            throw new MedicalRecordException("Patient has medical records, cannot delete.");
        }
        return patientService.delete(patientId, confirm);
    }
}