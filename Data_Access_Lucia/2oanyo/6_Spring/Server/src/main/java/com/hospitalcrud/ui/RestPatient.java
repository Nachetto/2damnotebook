package com.hospitalcrud.ui;

import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.domain.model.PatientUI;
import com.hospitalcrud.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin(origins = "http://127.0.0.1:5500")

@RequestMapping("/patients")
public class RestPatient {

    private final PatientService patientService;

    public RestPatient(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientUI> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping
    @RequestMapping("/{patientId}/medRecords")
    public List<MedRecordUI> getPatientMedRecords(@PathVariable int patientId) {
        return patientService.getPatientMedRecords(patientId);
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
        return patientService.delete(patientId, confirm);
    }
}