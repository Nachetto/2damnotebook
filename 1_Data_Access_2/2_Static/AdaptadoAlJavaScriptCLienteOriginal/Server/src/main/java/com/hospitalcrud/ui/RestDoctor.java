package com.hospitalcrud.ui;

import com.hospitalcrud.dao.model.Doctor;
import com.hospitalcrud.service.DoctorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/nacho/doctors")
public class RestDoctor {
    private DoctorService doctorService;
    public RestDoctor() {
        this.doctorService = new DoctorService();
    }

    @GetMapping
    public List<Doctor> getDoctors() {
        return doctorService.getDoctors();
    }
}
