import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// ---- Vista ----
public class EmpleadosVista extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtNombre, txtDepartamento;
    private EmpleadoDAO dao = new EmpleadoDAO();

    public EmpleadosVista() {
        setTitle("Ejercicio 6 - Gestión de Empleados");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel izquierdo - formulario
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Editar"));
        panelForm.setPreferredSize(new Dimension(250, 0));

        txtId = new JTextField(); txtId.setEditable(false);
        txtNombre = new JTextField();
        txtDepartamento = new JTextField();

        panelForm.add(new JLabel("ID:")); panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre:")); panelForm.add(txtNombre);
        panelForm.add(new JLabel("Departamento:")); panelForm.add(txtDepartamento);

        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        panelForm.add(btnModificar); panelForm.add(btnEliminar);

        // Panel derecho - tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Departamento"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtDepartamento.setText(modeloTabla.getValueAt(fila, 2).toString());
                }
            }
        });

        btnModificar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            int id = Integer.parseInt(txtId.getText());
            dao.modificar(new Empleado(id, txtNombre.getText(), txtDepartamento.getText()));
            cargarTabla();
            limpiarFormulario();
        });

        btnEliminar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            dao.eliminar(Integer.parseInt(txtId.getText()));
            cargarTabla();
            limpiarFormulario();
        });

        add(panelForm, BorderLayout.WEST);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarTabla();
        setVisible(true);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        dao.consultarTodos().forEach(emp ->
            modeloTabla.addRow(new Object[]{emp.getId(), emp.getNombre(), emp.getDepartamento()}));
    }

    private void limpiarFormulario() {
        txtId.setText(""); txtNombre.setText(""); txtDepartamento.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmpleadosVista::new);
    }
}
