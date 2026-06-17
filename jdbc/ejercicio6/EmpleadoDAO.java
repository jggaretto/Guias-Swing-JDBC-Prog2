import java.sql.*;
import java.util.ArrayList;

public class EmpleadoDAO implements OperacionesDAO {

    @Override
    public void insertar(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, departamento) VALUES (?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getDepartamento());
            pstmt.executeUpdate();
            System.out.println("Empleado insertado: " + empleado.getNombre());
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, departamento = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getDepartamento());
            pstmt.setInt(3, empleado.getId());
            int filas = pstmt.executeUpdate();
            System.out.println(filas > 0 ? "Empleado modificado." : "ID no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error al modificar: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filas = pstmt.executeUpdate();
            System.out.println(filas > 0 ? "Empleado eliminado." : "ID no encontrado.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Empleado> consultarTodos() {
        ArrayList<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Empleado(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("departamento")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e.getMessage());
        }
        return lista;
    }
}
