package com.hospitalcrud.ui;

import com.hospitalcrud.domain.model.DoctorUI;
import com.hospitalcrud.service.DoctorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/doctors")


public class RestDoctor {

    private final DoctorService doctorService;

    public RestDoctor(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<DoctorUI> getDoctors() {
        return doctorService.getDoctors();
    }
}