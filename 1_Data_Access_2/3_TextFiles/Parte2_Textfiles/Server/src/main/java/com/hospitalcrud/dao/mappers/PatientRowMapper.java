package com.hospitalcrud.dao.mappers;

import com.hospitalcrud.dao.model.Patient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@Log4j2
public class PatientRowMapper {
    // Para que lea la fecha 05/05/1990 necesitamos esto:
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Patient mapRow(String s) {
        //Coges la linea y la divides por el punto y coma
        String[] parts = s.split(";");

        // Si el tamaño de la cadena es menor a x numero de variables que vamos a usar abajo para crear el objeto
        // se imprime un mensaje de error
        if (parts.length < 4) {
            log.error("Invalid input: " + s);
            return null;
        }

        //coges los valores de la cadena y los asignas a las variables
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
