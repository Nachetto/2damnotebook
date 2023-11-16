package nacho.victor;

import nacho.victor.excepciones.FechaException;

import java.time.LocalDate;

public interface ComponenteFecha {
    LocalDate getDate() throws FechaException;
    void setDate(int day, int month, int year) ;
}
