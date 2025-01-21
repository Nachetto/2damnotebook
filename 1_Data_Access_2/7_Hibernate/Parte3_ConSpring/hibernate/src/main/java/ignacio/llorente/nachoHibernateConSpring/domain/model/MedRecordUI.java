package ignacio.llorente.nachoHibernateConSpring.domain.model;

import ignacio.llorente.nachoHibernateConSpring.dao.model.MedRecord;
import ignacio.llorente.nachoHibernateConSpring.dao.model.Medication;
import ignacio.llorente.nachoHibernateConSpring.service.DoctorService;
import ignacio.llorente.nachoHibernateConSpring.service.PatientService;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedRecordUI {

    private int id;
    private int idPatient;
    private int idDoctor;
    private String description;
    private String date;
    private List<String> medications;

    public MedRecord toMedRecord(PatientService patientService, DoctorService doctorService) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate datee = LocalDate.parse(this.date, formatter);
        //create the medications
        List<Medication> medicationsFinal = null;
        medications.forEach(medication -> {
            Medication med = new Medication();
            med.setMedicationName(medication);
            medicationsFinal.add(med);
        });

        return new MedRecord(id, patientService.getById(idPatient), doctorService.getDoctor(idDoctor), description, datee, medicationsFinal);
    }
}

