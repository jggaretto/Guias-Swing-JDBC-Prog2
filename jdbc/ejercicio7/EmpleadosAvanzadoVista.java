import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.io.File;

// ---- Modelo ----
class Empleado {
    private int id;
    private String nombre;
    private int idDepartamento;
    private String rutaFoto;

    public Empleado(int id, String nombre, int idDepartamento, String rutaFoto) {
        this.id = id; this.nombre = nombre; this.idDepartamento = idDepartamento; this.rutaFoto = rutaFoto;
    }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getIdDepartamento() { return idDepartamento; }
    public String getRutaFoto() { return rutaFoto; }
}

class Departamento {
    private int id;
    private String nombre;
    public Departamento(int id, String nombre) { this.id = id; this.nombre = nombre; }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    @Override public String toString() { return nombre; }
}

// ---- Conexión ----
class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa_db";
    private static final String USER = "root";
    private static final String PASSWORD = "tu_contrasena_aqui";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); return null; }
    }
}

// ---- DAO ----
class EmpleadoDAO {
    public ArrayList<Departamento> consultarDepartamentos() {
        ArrayList<Departamento> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM departamentos")) {
            while (rs.next()) lista.add(new Departamento(rs.getInt("id_depto"), rs.getString("nombre_depto")));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }

    public ArrayList<Empleado> consultarTodos() {
        ArrayList<Empleado> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM empleados")) {
            while (rs.next())
                lista.add(new Empleado(rs.getInt("id"), rs.getString("nombre"),
                        rs.getInt("id_departamento"), rs.getString("ruta_foto")));
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }

    public void insertar(Empleado emp) {
        String sql = "INSERT INTO empleados (nombre, id_departamento, ruta_foto) VALUES (?,?,?)";
        try (Connection conn = ConexionDB.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getNombre()); ps.setInt(2, emp.getIdDepartamento()); ps.setString(3, emp.getRutaFoto());
            ps.executeUpdate();
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    public void modificar(Empleado emp) {
        String sql = "UPDATE empleados SET nombre=?, id_departamento=?, ruta_foto=? WHERE id=?";
        try (Connection conn = ConexionDB.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getNombre()); ps.setInt(2, emp.getIdDepartamento());
            ps.setString(3, emp.getRutaFoto()); ps.setInt(4, emp.getId());
            ps.executeUpdate();
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }

    public void eliminar(int id) {
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM empleados WHERE id=?")) {
            ps.setInt(1, id); ps.executeUpdate();
        } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
}

// ---- Vista ----
public class EmpleadosAvanzadoVista extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtNombre;
    private JComboBox<Departamento> comboDepartamento;
    private JLabel lblFoto;
    private String rutaFotoSeleccionada = "";
    private EmpleadoDAO dao = new EmpleadoDAO();
    private ArrayList<Departamento> departamentos;

    public EmpleadosAvanzadoVista() {
        setTitle("Ejercicio 7 - CRUD Avanzado");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        departamentos = dao.consultarDepartamentos();

        // Panel izquierdo
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));
        panelForm.setPreferredSize(new Dimension(280, 0));

        txtId = new JTextField(); txtId.setEditable(false);
        txtNombre = new JTextField();
        comboDepartamento = new JComboBox<>(departamentos.toArray(new Departamento[0]));
        lblFoto = new JLabel("Sin foto", SwingConstants.CENTER);
        lblFoto.setPreferredSize(new Dimension(80, 80));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton btnBuscarFoto = new JButton("Buscar Foto");
        btnBuscarFoto.addActionListener(e -> buscarFoto());

        panelForm.add(new JLabel("ID:")); panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre:")); panelForm.add(txtNombre);
        panelForm.add(new JLabel("Departamento:")); panelForm.add(comboDepartamento);
        panelForm.add(new JLabel("Foto:")); panelForm.add(btnBuscarFoto);
        panelForm.add(lblFoto); panelForm.add(new JLabel());

        JButton btnInsertar = new JButton("Insertar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        panelForm.add(btnInsertar); panelForm.add(btnModificar);
        panelForm.add(btnEliminar); panelForm.add(new JLabel());

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "ID Depto", "Foto"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { seleccionarFila(); }
        });

        btnInsertar.addActionListener(e -> {
            Departamento dep = (Departamento) comboDepartamento.getSelectedItem();
            if (dep == null) return;
            dao.insertar(new Empleado(0, txtNombre.getText(), dep.getId(), rutaFotoSeleccionada));
            cargarTabla(); limpiar();
        });

        btnModificar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            Departamento dep = (Departamento) comboDepartamento.getSelectedItem();
            dao.modificar(new Empleado(Integer.parseInt(txtId.getText()), txtNombre.getText(),
                    dep.getId(), rutaFotoSeleccionada));
            cargarTabla(); limpiar();
        });

        btnEliminar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            dao.eliminar(Integer.parseInt(txtId.getText()));
            cargarTabla(); limpiar();
        });

        add(panelForm, BorderLayout.WEST);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        cargarTabla();
        setVisible(true);
    }

    private void buscarFoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            rutaFotoSeleccionada = archivo.getAbsolutePath();
            ImageIcon icon = new ImageIcon(new ImageIcon(rutaFotoSeleccionada)
                    .getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            lblFoto.setIcon(icon);
            lblFoto.setText("");
        }
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        int idDepto = Integer.parseInt(modeloTabla.getValueAt(fila, 2).toString());
        for (int i = 0; i < departamentos.size(); i++) {
            if (departamentos.get(i).getId() == idDepto) { comboDepartamento.setSelectedIndex(i); break; }
        }
        rutaFotoSeleccionada = modeloTabla.getValueAt(fila, 3) != null ? modeloTabla.getValueAt(fila, 3).toString() : "";
        if (!rutaFotoSeleccionada.isEmpty()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(rutaFotoSeleccionada)
                    .getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            lblFoto.setIcon(icon); lblFoto.setText("");
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        dao.consultarTodos().forEach(emp ->
            modeloTabla.addRow(new Object[]{emp.getId(), emp.getNombre(), emp.getIdDepartamento(), emp.getRutaFoto()}));
    }

    private void limpiar() {
        txtId.setText(""); txtNombre.setText(""); rutaFotoSeleccionada = "";
        lblFoto.setIcon(null); lblFoto.setText("Sin foto");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmpleadosAvanzadoVista::new);
    }
}
