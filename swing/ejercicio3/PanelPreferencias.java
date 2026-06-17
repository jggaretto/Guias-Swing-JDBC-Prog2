import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelPreferencias extends JFrame {
    private JLabel etiquetaOpciones;

    public PanelPreferencias() {
        setTitle("Ejercicio 3 - Panel de Preferencias");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        etiquetaOpciones = new JLabel("Opciones seleccionadas: Ninguna");
        panel.add(etiquetaOpciones);

        JButton botonConfigurar = new JButton("Configurar Preferencias");
        botonConfigurar.addActionListener(e -> {
            DialogoPreferencias dialogo = new DialogoPreferencias(PanelPreferencias.this);
            dialogo.setVisible(true);
            String seleccionadas = dialogo.getOpcionesSeleccionadas();
            etiquetaOpciones.setText("Opciones seleccionadas: " +
                    (seleccionadas.isEmpty() ? "Ninguna" : seleccionadas));
        });
        panel.add(botonConfigurar);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PanelPreferencias::new);
    }
}

class DialogoPreferencias extends JDialog {
    private JCheckBox chkModoOscuro, chkNotificaciones, chkAutoguardado;
    private String opcionesSeleccionadas = "";

    public DialogoPreferencias(JFrame padre) {
        super(padre, "Preferencias", true);
        setSize(250, 200);
        setLocationRelativeTo(padre);

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        chkModoOscuro = new JCheckBox("Modo Oscuro");
        chkNotificaciones = new JCheckBox("Notificaciones");
        chkAutoguardado = new JCheckBox("Autoguardado");

        panel.add(chkModoOscuro);
        panel.add(chkNotificaciones);
        panel.add(chkAutoguardado);

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            if (chkModoOscuro.isSelected()) sb.append("Modo Oscuro, ");
            if (chkNotificaciones.isSelected()) sb.append("Notificaciones, ");
            if (chkAutoguardado.isSelected()) sb.append("Autoguardado, ");
            opcionesSeleccionadas = sb.toString().replaceAll(", $", "");
            dispose();
        });
        panel.add(botonAceptar);

        add(panel);
    }

    public String getOpcionesSeleccionadas() {
        return opcionesSeleccionadas;
    }
}
