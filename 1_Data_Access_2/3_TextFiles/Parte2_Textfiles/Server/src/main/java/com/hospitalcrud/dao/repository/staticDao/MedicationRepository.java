package com.hospitalcrud.dao.repository.staticDao;

import com.hospitalcrud.dao.model.Medication;
import com.hospitalcrud.dao.repository.MedicationDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("txt")
public class MedicationRepository implements MedicationDAO {
    //creando la lista estatica de medicamentos y sus dosis
    private static List<Medication> medications = new ArrayList<>(List.of(
            new Medication(1, "Paracetamol", 1),
            new Medication(2, "Ibuprofeno", 2),
            new Medication(3, "Amoxicilina", 3),
            new Medication(4, "Dexametasona", 4)
    ));

    @Override
    public List<Medication> getAll() {
        return medications;
    }

    @Override
    public int save(Medication m) {
        return 0;
    }

    @Override
    public void update(Medication m) {
        //not implemented on the js client
    }

    @Override
    public boolean delete(int id, boolean confirmation) {
        return false;
    }
}
