package com.hospitalcrud.dao.mappers;

import com.hospitalcrud.dao.model.Credential;
import com.hospitalcrud.dao.model.Patient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@Log4j2
public class PatientRowMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Patient mapRow(String s) {
        String[] parts = s.split(";");

        if (parts.length < 4) {
            log.error("Invalid input: " + s);
            return null;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            LocalDate birthDate = LocalDate.parse(parts[2], DATE_FORMATTER);
            String phone = parts[3];

            return new Patient(id, name, birthDate, phone);
        } catch (DateTimeParseException e) {
            log.error("Error parsing date for input: " + s);
        } catch (NumberFormatException e) {
            log.error("Error parsing number for input: " + s);
        }
        return null;
    }
}
