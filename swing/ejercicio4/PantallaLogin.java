import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class PantallaLogin extends JFrame implements ActionListener {
    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private static final String USUARIO_CORRECTO = "admin";
    private static final char[] PASSWORD_CORRECTA = "1234".toCharArray();

    public PantallaLogin() {
        setTitle("Ejercicio 4 - Pantalla de Login");
        setSize(320, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Usuario:"));
        campoUsuario = new JTextField();
        panel.add(campoUsuario);

        panel.add(new JLabel("Contraseña:"));
        campoPassword = new JPasswordField("", 20);
        campoPassword.setEchoChar('*');
        panel.add(campoPassword);

        JButton botonAcceder = new JButton("Acceder");
        botonAcceder.addActionListener(this);
        panel.add(new JLabel());
        panel.add(botonAcceder);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String usuario = campoUsuario.getText().trim();
        char[] password = campoPassword.getPassword();

        if (usuario.equals(USUARIO_CORRECTO) && Arrays.equals(password, PASSWORD_CORRECTA)) {
            JOptionPane.showMessageDialog(this, "¡Acceso correcto! Bienvenido, " + usuario + ".",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        Arrays.fill(password, '\0');
        campoPassword.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PantallaLogin::new);
    }
}
