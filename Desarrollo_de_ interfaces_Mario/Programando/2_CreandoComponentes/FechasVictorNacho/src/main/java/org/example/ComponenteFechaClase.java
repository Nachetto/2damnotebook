package org.example;

import javax.swing.*;
import java.awt.*;
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
        yearTextField = new JTextField(4); // Un campo de texto para el año con ancho para 4 caracteres

        // Poblar el JComboBox con los días del mes (considerando un máximo de 31 días)
        IntStream.rangeClosed(1, 31).forEach(day -> dayComboBox.addItem(day));

        // Poblar el JComboBox con los meses
        IntStream.rangeClosed(1, 12).forEach(month -> monthComboBox.addItem(month));

        // Establecer un Layout y añadir los componentes
        setLayout(new FlowLayout());
        add(dayComboBox);
        add(monthComboBox);
        add(yearTextField);
    }

    @Override
    public LocalDate getDate() {
        try {
            int day = (int) dayComboBox.getSelectedItem();
            int month = (int) monthComboBox.getSelectedItem();
            int year = Integer.parseInt(yearTextField.getText());
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            // Manejar la excepción si el año no es un número o la fecha no es válida
            JOptionPane.showMessageDialog(this, "La fecha introducida no es válida: " + e.getMessage(), "Error de Fecha", JOptionPane.ERROR_MESSAGE);
            return null;
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
            // Puedes manejar excepciones específicas dependiendo de cómo se haya implementado getDate
            JOptionPane.showMessageDialog(null, "Error al verificar las fechas: " + e.getMessage());
        }
    }
}
