import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Mi Aplicación Swing");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Agregar un botón
        JButton miBoton = new JButton("Haz clic");
        miBoton.setBounds(100, 100, 200, 30);

        // Agregar el botón a la ventana
        add(miBoton);

        setLayout(null); // Usar un diseño absoluto

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main ventana = new Main();
            ventana.setVisible(true);
        });
    }
}
