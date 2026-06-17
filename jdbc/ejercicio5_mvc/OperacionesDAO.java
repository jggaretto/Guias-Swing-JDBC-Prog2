import java.util.ArrayList;

public interface OperacionesDAO {
    void insertar(Empleado empleado);
    void modificar(Empleado empleado);
    void eliminar(int id);
    ArrayList<Empleado> consultarTodos();
}
