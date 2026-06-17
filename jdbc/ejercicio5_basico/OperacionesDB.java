import java.sql.*;

public class OperacionesDB {

    public void insertarEmpleado(String nombre, String departamento) {
        String sql = "INSERT INTO empleados (nombre, departamento) VALUES (?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, departamento);
            pstmt.executeUpdate();
            System.out.println("Empleado '" + nombre + "' insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    public void actualizarDepartamento(int id, String nuevoDepartamento) {
        String sql = "UPDATE empleados SET departamento = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoDepartamento);
            pstmt.setInt(2, id);
            int filas = pstmt.executeUpdate();
            if (filas > 0)
                System.out.println("Departamento del ID " + id + " actualizado a: " + nuevoDepartamento);
            else
                System.out.println("No se encontró el ID: " + id);
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public void borrarEmpleado(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filas = pstmt.executeUpdate();
            if (filas > 0)
                System.out.println("Empleado con ID " + id + " borrado.");
            else
                System.out.println("No se encontró el ID: " + id);
        } catch (SQLException e) {
            System.out.println("Error al borrar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        OperacionesDB ops = new OperacionesDB();
        ops.insertarEmpleado("Ana García", "Recursos Humanos");
        ops.actualizarDepartamento(1, "Finanzas");
        ops.borrarEmpleado(1);
    }
}
