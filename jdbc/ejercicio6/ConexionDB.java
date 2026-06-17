import java.sql.*;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }
}
