package com.hospitalcrud.ui;

import com.hospitalcrud.domain.model.MedicalRecordUI;
import com.hospitalcrud.domain.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RestMedicalRecord {
    private final MedicalRecordService medicalRecordService;

    public RestMedicalRecord(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }


    @GetMapping("/patients/{patientId}/medRecords")
    public List<MedicalRecordUI> getMedicalRecords(@PathVariable int patientId) {
        return medicalRecordService.getMedicalRecords(patientId);
    }

    @PostMapping("/patients/medRecords")
    public int addMedRecord(@RequestBody MedicalRecordUI medicalRecordUI) {
        return medicalRecordService.addMedicalRecord(medicalRecordUI);

    }

    @DeleteMapping("/patients/medRecords/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedRecord(@PathVariable int id) {
        medicalRecordService.deleteMedicalRecord(id);
    }

    @PutMapping("/patients/medRecords")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedRecord(@RequestBody MedicalRecordUI medicalRecordUI) {
        medicalRecordService.updateMedicalRecord(medicalRecordUI);
    }

}
