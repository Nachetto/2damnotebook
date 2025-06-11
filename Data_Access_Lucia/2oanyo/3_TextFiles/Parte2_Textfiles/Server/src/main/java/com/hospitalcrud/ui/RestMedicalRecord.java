package com.hospitalcrud.ui;

import com.hospitalcrud.domain.error.BadRequestException;
import com.hospitalcrud.domain.error.InternalServerErrorException;
import com.hospitalcrud.domain.error.NotFoundException;
import com.hospitalcrud.domain.model.MedRecordUI;
import com.hospitalcrud.service.MedRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/patients/medRecords")
public class RestMedicalRecord {

    private final MedRecordService medRecordService;

    public RestMedicalRecord(MedRecordService medRecordService) {
        this.medRecordService = medRecordService;
    }

    // Exception handling
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

    @GetMapping
    public List<MedRecordUI> getRecordsFromPatientID(@PathVariable int patientId)  {
        return medRecordService.getAll().stream().filter(m -> m.getIdPatient() == patientId).toList();
    }

    @PostMapping
    public int addMedRecord(@RequestBody MedRecordUI medRecordUI) {
        return medRecordService.add(medRecordUI);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedRecord(@RequestBody MedRecordUI medRecordUI) {
        medRecordService.update(medRecordUI);
    }

    @DeleteMapping("/{medRecordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //queryparam confirmation boolean
    public void deleteMedRecord(@PathVariable int medRecordId) {
        medRecordService.delete(medRecordId);
    }
}