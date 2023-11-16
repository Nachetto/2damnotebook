package org.example;

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

        // Contenedor principal con BoxLayout
        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        // Panel para la fecha de inicio
        JPanel panelFechaInicio = new JPanel();
        panelFechaInicio.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelFechaInicio = new JLabel("Fecha de Inicio:");
        ComponenteFecha componenteFechaInicio = new ComponenteFechaClase();
        panelFechaInicio.add(labelFechaInicio);
        panelFechaInicio.add((Component) componenteFechaInicio);

        // Panel para la fecha de fin
        JPanel panelFechaFin = new JPanel();
        panelFechaFin.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelFechaFin = new JLabel("Fecha de Fin:");
        ComponenteFecha componenteFechaFin = new ComponenteFechaClase();
        panelFechaFin.add(labelFechaFin);
        panelFechaFin.add((Component) componenteFechaFin);

        // Panel para el botón de verificar
        JPanel panelBotonVerificar = new JPanel();
        panelBotonVerificar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton botonVerificar = new JButton("Verificar");
        botonVerificar.addActionListener(e -> verificarFechas(componenteFechaInicio, componenteFechaFin));
        panelBotonVerificar.add(botonVerificar);

        // Agregar paneles al contenedor principal
        mainContainer.add(panelFechaInicio);
        mainContainer.add(panelFechaFin);
        mainContainer.add(panelBotonVerificar);

        // Ajustar la ventana y hacerla visible
        pack();
        setVisible(true);


        botonVerificar.addActionListener(e -> {
            // Crea una instancia de ComponenteFechaClase
            ComponenteFechaClase componenteFechaClase = new ComponenteFechaClase();
            // Llama al método cuando se presione el botón
            componenteFechaClase.onBotonVerificarPresionado(componenteFechaInicio, componenteFechaFin);
        });
    }

    //listener para el boton de verificar

    // En la clase ComponenteFechaClase
    public void onBotonVerificarPresionado(ComponenteFecha fechaInicio, ComponenteFecha fechaFin) {
        // Aquí va la lógica que se ejecutará cuando se presione el botón
    }

    // En el lugar donde se crea el botón


    private void verificarFechas(ComponenteFecha fechaInicio, ComponenteFecha fechaFin) {
        // Aquí va la lógica para verificar las fechas
        // Por ejemplo, puedes comparar las fechas y mostrar un mensaje
    }
}
