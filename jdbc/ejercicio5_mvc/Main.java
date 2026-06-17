
public class Main {
    public static void main(String[] args) {
        EmpleadoDAO dao = new EmpleadoDAO();

        // INSERT
        dao.insertar(new Empleado("Ana García", "Marketing"));
        dao.insertar(new Empleado("Carlos López", "Sistemas"));

        // SELECT
        System.out.println("--- Todos los empleados ---");
        dao.consultarTodos().forEach(System.out::println);

        // UPDATE (suponiendo que Ana tiene ID 1)
        dao.modificar(new Empleado(1, "Ana García", "Finanzas"));

        // DELETE
        dao.eliminar(2);

        System.out.println("--- Empleados tras cambios ---");
        dao.consultarTodos().forEach(System.out::println);
    }
}
