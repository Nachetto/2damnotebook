package com.hospitalcrud.ui;

import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.domain.service.MedicationsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RestMedication {

    private final MedicationsService medicationsService;

    public RestMedication(MedicationsService medicationsService) {
        this.medicationsService = medicationsService;
    }

    @GetMapping("/medications")
    public List<String> getMedications() {
        return medicationsService.getAll();
    }
}
