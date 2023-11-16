package nacho.victor;

import nacho.victor.excepciones.FechaException;
import nacho.victor.excepciones.FechaImposibleException;
import nacho.victor.excepciones.FechaIncompletaException;
import nacho.victor.excepciones.FechaIncorrectaException;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.IntStream;

public class ComponenteFechaClase extends JPanel implements ComponenteFecha{
    private JComboBox<Integer> dayComboBox;
    private JComboBox<Integer> monthComboBox;
    private JTextField yearTextField;

    public ComponenteFechaClase() {
        dayComboBox = new JComboBox<>();
        monthComboBox = new JComboBox<>();
        yearTextField = new JTextField(4);

        IntStream.rangeClosed(1, 31).forEach(day -> dayComboBox.addItem(day));

        IntStream.rangeClosed(1, 12).forEach(month -> monthComboBox.addItem(month));

        setLayout(new FlowLayout());
        add(dayComboBox);
        add(monthComboBox);
        add(yearTextField);
    }

    @Override
    public LocalDate getDate() throws FechaException {
        Integer day = (Integer) dayComboBox.getSelectedItem();
        Integer month = (Integer) monthComboBox.getSelectedItem();
        String yearString = yearTextField.getText();

        if (day == null || month == null || yearString.isEmpty()) {
                throw new FechaIncompletaException();
        }

        try {
            int year = Integer.parseInt(yearString);
            return LocalDate.of(year, month, day);
        } catch (NumberFormatException e) {
            throw new FechaIncorrectaException("El año debe ser un número válido. "+e.getMessage());
        } catch (DateTimeException e) {
            throw new FechaImposibleException("La combinación de día, mes y año no forma una fecha válida. "+e.getMessage());
        }
    }

    @Override
    public void setDate(int day, int month, int year) {
        dayComboBox.setSelectedItem(day);
        monthComboBox.setSelectedItem(month);
        yearTextField.setText(String.valueOf(year));
    }


    public void onBotonVerificarPresionado(ComponenteFecha componenteFechaInicio, ComponenteFecha componenteFechaFin) {
        try {
            LocalDate fechaInicio = componenteFechaInicio.getDate();
            LocalDate fechaFin = componenteFechaFin.getDate();

            if (fechaInicio == null || fechaFin == null) {
                JOptionPane.showMessageDialog(null, "Una o ambas fechas no están establecidas correctamente.");
                return;
            }
            if (fechaInicio.isAfter(fechaFin)) {
                JOptionPane.showMessageDialog(null, "Error: La fecha de inicio es posterior a la fecha de fin.");
            } else if (fechaInicio.isBefore(fechaFin)) {
                long diasDiferencia = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
                JOptionPane.showMessageDialog(null, "La diferencia entre las fechas es de " + diasDiferencia + " días.");
            } else {
                JOptionPane.showMessageDialog(null, "Las fechas de inicio y fin son iguales.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
