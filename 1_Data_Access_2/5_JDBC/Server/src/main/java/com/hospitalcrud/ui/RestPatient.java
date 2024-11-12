package com.hospitalcrud.ui;

import com.hospitalcrud.domain.error.BadRequestException;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import com.hospitalcrud.domain.error.MedicalRecordException;
import com.hospitalcrud.domain.error.NotFoundException;
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


    //manejo de excepciones
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleBadRequest(BadRequestException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFound(NotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleInternalServerError(InternalServerErrorException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MedicalRecordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleMedicalRecordException(MedicalRecordException e) {
        return e.getMessage();
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