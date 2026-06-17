import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormularioBasico extends JFrame implements ActionListener {
    private JTextField campoNombre;
    private JLabel etiquetaResultado;

    public FormularioBasico() {
        setTitle("Ejercicio 1 - Formulario Básico");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Introduce tu nombre:"));
        campoNombre = new JTextField(20);
        panel.add(campoNombre);

        JButton botonSaludar = new JButton("Saludar");
        botonSaludar.addActionListener(this);
        panel.add(botonSaludar);

        etiquetaResultado = new JLabel("");
        panel.add(etiquetaResultado);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre = campoNombre.getText().trim();
        if (!nombre.isEmpty()) {
            etiquetaResultado.setText("¡Hola, " + nombre + "!");
        } else {
            etiquetaResultado.setText("Por favor ingresa un nombre.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormularioBasico::new);
    }
}
