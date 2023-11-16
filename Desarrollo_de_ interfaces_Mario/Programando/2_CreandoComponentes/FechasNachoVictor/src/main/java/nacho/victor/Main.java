package nacho.victor;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
        });
    }

    public Main() {
        setTitle("Swing Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);

        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        JPanel panelFechaInicio = new JPanel();
        panelFechaInicio.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelFechaInicio = new JLabel("Fecha de Inicio:");
        ComponenteFecha componenteFechaInicio = new ComponenteFechaClase();
        panelFechaInicio.add(labelFechaInicio);
        panelFechaInicio.add((Component) componenteFechaInicio);

        JPanel panelFechaFin = new JPanel();
        panelFechaFin.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelFechaFin = new JLabel("Fecha de Fin:");
        ComponenteFecha componenteFechaFin = new ComponenteFechaClase();
        panelFechaFin.add(labelFechaFin);
        panelFechaFin.add((Component) componenteFechaFin);

        JPanel panelBotonVerificar = new JPanel();
        panelBotonVerificar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton botonVerificar = new JButton("Verificar");
        botonVerificar.addActionListener(e -> verificarFechas(componenteFechaInicio, componenteFechaFin));
        panelBotonVerificar.add(botonVerificar);

        mainContainer.add(panelFechaInicio);
        mainContainer.add(panelFechaFin);
        mainContainer.add(panelBotonVerificar);

        pack();
        setVisible(true);
    }

    private void verificarFechas(ComponenteFecha fechaInicio, ComponenteFecha fechaFin) {
        ComponenteFechaClase componenteFechaClase = new ComponenteFechaClase();
        componenteFechaClase.onBotonVerificarPresionado(fechaInicio, fechaFin);
    }
}
