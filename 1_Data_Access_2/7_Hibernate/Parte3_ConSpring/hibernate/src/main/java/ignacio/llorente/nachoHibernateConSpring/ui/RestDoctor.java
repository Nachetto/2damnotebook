package ignacio.llorente.nachoHibernateConSpring.ui;

import ignacio.llorente.nachoHibernateConSpring.dao.model.Doctor;
import ignacio.llorente.nachoHibernateConSpring.service.DoctorService;
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
    public List<Doctor> getDoctors() {
        return doctorService.getDoctors();
    }
}