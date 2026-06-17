import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MiniEditor extends JFrame {
    private JTextArea areaTexto;

    public MiniEditor() {
        setTitle("Ejercicio 2 - Mini Editor");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        areaTexto = new JTextArea();
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem itemLimpiar = new JMenuItem("Limpiar texto");
        itemLimpiar.addActionListener(e -> areaTexto.setText(""));

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemLimpiar);
        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MiniEditor::new);
    }
}
