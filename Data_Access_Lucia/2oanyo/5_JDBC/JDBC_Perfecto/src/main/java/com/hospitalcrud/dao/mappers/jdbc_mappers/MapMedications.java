package com.hospitalcrud.dao.mappers.jdbc_mappers;

import com.hospitalcrud.dao.model.Medication;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MapMedications {
    public List<Medication> readRS (ResultSet rs) {
        List<Medication> medications = new ArrayList<>();
        try {
            while (rs.next()) medications.add(new Medication(
                    rs.getInt("prescription_id"),
                    rs.getString("medication_name"),
                    rs.getInt("record_id"),
                    rs.getString("dosage")
            ));
            return medications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
